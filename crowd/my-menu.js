function generateTree() {
    $.ajax({
        "url": "menu/get/whole/tree.json",
        "type":"post",
        "dataType":"json",
        "success":function(response){
            var result=response.result;
            if (result=="SUCCESS"){
                var setting={
                    "view":{
                        "addDiyDom":myAddDiyDom,
                        "addHoverDom":myAddHoverDom,
                        "removeHoverDom":myRemoveHoverDom

                    },
                    "data":{
                        "key":{
                            "url":"shiyutao"
                        }

                    }


                };
                var zNodes=response.data;
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            }
            if (result=="FAILED"){
                Layer.msg(response.message);
            }
        }
    });


}
function myAddDiyDom(treeId, treeNode) {
    /*console.log(treeNode);*/
    var spanId = treeNode.tId + "_ico";
    $("#"+spanId).removeClass().addClass(treeNode.icon);

}
function myAddHoverDom(treeId, treeNode) {
    var anchorId= treeNode.tId + "_a";
    var btnGroupId=treeNode.tId + "_btnGrp";
    if ($("#"+btnGroupId).length>0){
        return;
    }

    var addBtn = "<a id='"+treeNode.id+"' class=' addBtn btn btn-info dropdown-toggle btn-xs'  style='margin-left:10px;padding-top:0px;' href='#' title='添加子节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
    var removeBtn = "<a id='"+treeNode.id+"' class=' removeBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='删除子节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
    var editBtn = "<a  id='"+treeNode.id+"' class=' editBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='修改权限信息'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";
    var level=treeNode.level;
    var btnHTML="";

    if (level==0){
        btnHTML=addBtn;

    }
    if (level==1){
        btnHTML=addBtn+" "+editBtn;
        var length=treeNode.children.length;
        if (length==0){
            btnHTML=btnHTML+""+removeBtn;
        }

    }
    if (level==2){
        btnHTML=editBtn+" "+removeBtn;

    }

    $("#"+anchorId).after("<span id='"+btnGroupId+"'>"+btnHTML+"</span>>");
}
function myRemoveHoverDom(treeId, treeNode) {
    var btnGroupId=treeNode.tId + "_btnGrp";
    $("#"+btnGroupId).remove();
}









