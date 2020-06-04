/**
 * 评论公共方法
 */
function reply(targetId,type,content) {
    if(!(content.trim())){
        alert("评价不能为空哦！");
        return ;
    }
    $.ajax({
        url:"/comment",
        type:"post",
        dataType:"json",
        contentType:"application/json",
        data:JSON.stringify({
            "parentID":targetId,
            "content":content,
            "type":type
        }),
        success:function(result){
            if(result.code == 200){
                $("#commentContent").val("");
                window.location.reload();
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

/**
 * 回复问题
 */
function replyToQuestion() {
    var questionId = $("#questionId").val();
    var commentContent = $("#commentContent").val();
    reply(questionId,1,commentContent);
}

/**
 * 回复评论
 */
function replyToComment(e) {
    var id=e.getAttribute("data");
    var content=$("#input-" + id).val();
    reply(id,2,content);
}

/**
 * 二级评论
 */
function collapseComment(e) {
    var id=e.getAttribute("data-id");
    var dataId=$("#data-" + id);
    if (dataId.hasClass("in")){
        dataId.removeClass("in");
        e.classList.remove("active");
    }else{
        var subCommentContainer = $("#data-" + id);
        $.getJSON("/comment/" + id, function (data) {
            $.each(data.data.reverse(), function (index, comment) {
                var mediaLeftElement = $("<div/>", {
                    "class": "media-left"
                }).append($("<img/>", {
                    "class": "media-object img-rounded",
                    "src": comment.user.headimageUrl
                }));

                var mediaBodyElement = $("<div/>", {
                    "class": "media-body"
                }).append($("<h5/>", {
                    "class": "media-heading",
                    "html": comment.user.name
                })).append($("<div/>", {
                    "html": comment.content
                })).append($("<div/>", {
                    "class": "menu"
                }).append($("<span/>", {
                    "class": "pull-right",
                    "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                })));

                var mediaElement = $("<div/>", {
                    "class": "media"
                }).append(mediaLeftElement).append(mediaBodyElement);

                var commentElement = $("<div/>", {
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12"
                }).append(mediaElement).append("<hr style='margin-top: 0px'>");

                subCommentContainer.prepend(commentElement);
            });

            dataId.addClass("in");
            e.classList.add("active");
        });
    }
}

function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if(previous.split("，").length>5){
            alert("最多选择5个标签！")
            return ;
        }
        if (previous) {
            $("#tag").val(previous + '，' + value);
        } else {
            $("#tag").val(value);
        }
    }
}