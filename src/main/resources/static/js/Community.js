function reply() {
    var questionId = $("#questionId").val();
    var commentContent = $("#commentContent").val();
    $.ajax({
        url:"/comment",
        type:"post",
        dataType:"json",
        contentType:"application/json",
        data:JSON.stringify({
            "parentID":questionId,
            "content":commentContent,
            "type":1
        }),
        success:function(result){
            if(result.code == 200){
                $("#commentContent").val("");

            }else{
                if(result.code == 1003){
                    var conf = confirm(result.message);
                    if (conf){
                        window.localStorage.setItem("closed",true);
                        window.open("https://github.com/login/oauth/authorize?client_id=db24f8ed06d20eb8add8&redirect_uri=http://localhost:8088/callback&scope=user&state=1");
                    }
                }else{
                    alert(result.message);
                }

            }
        }
    });
}