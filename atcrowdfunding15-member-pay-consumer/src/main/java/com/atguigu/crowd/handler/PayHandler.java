package com.atguigu.crowd.handler;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.atguigu.crowd.MySQLRemoteService;
import com.atguigu.crowd.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.atguigu.crowd.config.PayProperties;
import com.atguigu.crowd.entity.vo.OrderProjectVO;
import com.atguigu.crowd.entity.vo.OrderVO;


@Controller
public class PayHandler {
	
	@Autowired
	private PayProperties payProperties;
	
	@Autowired
	private MySQLRemoteService mySQLRemoteService;
	
	private Logger logger = LoggerFactory.getLogger(PayHandler.class);
	
	@RequestMapping("/notify")
	public void notifyUrlMethod(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {

		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		
		boolean signVerified = AlipaySignature.rsaCheckV1(
				params, 
				payProperties.getAlipayPublicKey(), 
				payProperties.getCharset(), 
				payProperties.getSignType());
	

		if(signVerified) {
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			
			logger.info("out_trade_no="+out_trade_no);
			logger.info("trade_no="+trade_no);
			logger.info("trade_status="+trade_status);
			
		}else {
			
			logger.info("验证失败");
		}
		
	}
	
	@ResponseBody
	@RequestMapping("/return")
	public String returnUrlMethod(HttpServletRequest request, HttpSession session) throws AlipayApiException, UnsupportedEncodingException {
		// 获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		
		boolean signVerified = AlipaySignature.rsaCheckV1(
				params, 
				payProperties.getAlipayPublicKey(), 
				payProperties.getCharset(), 
				payProperties.getSignType()); //调用SDK验证签名
	
		// ——请在这里编写您的程序（以下代码仅作参考）——
		if(signVerified) {
			String orderNum = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

			String payOrderNum = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			String orderAmount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

			OrderVO orderVO = (OrderVO) session.getAttribute("orderVO");

			orderVO.setPayOrderNum(payOrderNum);
			

			ResultEntity<String> resultEntity = mySQLRemoteService.saveOrderRemote(orderVO);
			logger.info("Order save result="+resultEntity.getResult());

			return "trade_no:"+payOrderNum+"<br/>out_trade_no:"+orderNum+"<br/>total_amount:"+orderAmount;
		}else {

			return "验签失败";
			
		}
	}
	
	// 这里必须加@ResponseBody注解，让当前方法的返回值成为响应体，在浏览器界面上显示支付宝支付界面
	@ResponseBody
	@RequestMapping("/generate/order")
	public String generateOrder(HttpSession session, OrderVO orderVO) throws AlipayApiException {

		OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");

		orderVO.setOrderProjectVO(orderProjectVO);

		String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		String user = UUID.randomUUID().toString().replace("-", "").toUpperCase();

		String orderNum = time + user;

		orderVO.setOrderNum(orderNum);

		Double orderAmount = (double) (orderProjectVO.getSupportPrice() * orderProjectVO.getReturnCount() + orderProjectVO.getFreight());
		orderVO.setOrderAmount(orderAmount);

		session.setAttribute("orderVO", orderVO);

		return sendRequestToAliPay(orderNum, orderAmount, orderProjectVO.getProjectName(), orderProjectVO.getReturnContent());
		
	}
	

	private String sendRequestToAliPay(String outTradeNo, Double totalAmount, String subject, String body) throws AlipayApiException {

		AlipayClient alipayClient = new DefaultAlipayClient(
				payProperties.getGatewayUrl(), 
				payProperties.getAppId(), 
				payProperties.getMerchantPrivateKey(), 
				"json", 
				payProperties.getCharset(), 
				payProperties.getAlipayPublicKey(), 
				payProperties.getSignType());

		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(payProperties.getReturnUrl());
		alipayRequest.setNotifyUrl(payProperties.getNotifyUrl());
		
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ outTradeNo +"\"," 
				+ "\"total_amount\":\""+ totalAmount +"\"," 
				+ "\"subject\":\""+ subject +"\"," 
				+ "\"body\":\""+ body +"\"," 
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

		return alipayClient.pageExecute(alipayRequest).getBody();
		
	}

}
