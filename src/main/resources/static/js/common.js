let pageNum = 1;
const Toast = Swal.mixin({
    position: 'top',
    showConfirmButton: false,
    timer: 3000,
    backdrop: false,
    width: 300,
    allowOutsideClick: false,
    timerProgressBar: true,
});
$(document).on("click", "#logout", function () {
    $.get("api/logout", function (result) {
        if (result.status === "success") {
            //Cookies.remove('user');
            window.location.href = "login";
        } else {
            alert(result.message);
        }
    });
});

$(function () {
    var pathName = window.document.location.pathname;
    var firstIndex = pathName.indexOf("\/");
    var secondIndex = pathName.indexOf("\/", firstIndex + 1);
    pathName = pathName.substring(secondIndex + 1, pathName.length);
    $(".nav-link[href='" + pathName + window.location.search + "']").addClass("active");
    $(".nav-treeview:has(.active)").siblings(".nav-link").addClass("active").parent().addClass("menu-open");
})

//构建分页模块
function buildPageNav(result) {
    //分页信息
    var info = "当前显示第" + result.pageInfo.startRow + "-" + result.pageInfo.endRow + "条记录 共" + result.pageInfo.total + "条记录";
    $("#paginationInfo").empty().text(info);

    //分页按钮
    var ul = $("<ul></ul>").addClass("pagination pagination-sm m-0 float-right");

    //构建元素
    var firstPageLi = $("<li></li>").addClass("page-item");
    $("<a></a>").addClass("page-link").attr("href", "#").append("首页").attr("href", "#").appendTo(firstPageLi);
    var prePageLi = $("<li></li>").addClass("page-item");
    $("<a></a>").addClass("page-link").attr("href", "#").append("&laquo;").appendTo(prePageLi);
    if (result.pageInfo.hasPreviousPage === false) {
        firstPageLi.addClass("disabled");
        prePageLi.addClass("disabled");
    } else {
        //为元素添加点击翻页的事件
        firstPageLi.click(function () {
            toPage(1);
        });
        prePageLi.click(function () {
            toPage(result.pageInfo.pageNum - 1);
        });
    }

    var nextPageLi = $("<li></li>").addClass("page-item");
    $("<a></a>").addClass("page-link").attr("href", "#").append("&raquo;").appendTo(nextPageLi);
    var lastPageLi = $("<li></li>").addClass("page-item");
    $("<a></a>").addClass("page-link").attr("href", "#").append("末页").attr("href", "#").appendTo(lastPageLi);
    if (result.pageInfo.hasNextPage === false) {
        nextPageLi.addClass("disabled");
        lastPageLi.addClass("disabled");
    } else {
        nextPageLi.click(function () {
            toPage(result.pageInfo.pageNum + 1);
        });
        lastPageLi.click(function () {
            toPage(result.pageInfo.pages);
        });
    }

    //添加首页和前一页 的提示
    ul.append(firstPageLi).append(prePageLi);
    //1,2，3遍历给ul中添加页码提示
    $.each(result.pageInfo.navigatepageNums, function (index, item) {
        var numLi = $("<li></li>").addClass("page-item");
        $("<a></a>").addClass("page-link").attr("href", "#").append(item).appendTo(numLi);
        if (result.pageInfo.pageNum === item) {
            numLi.addClass("active");
        }
        numLi.click(function () {
            toPage(item);
        });
        ul.append(numLi);
    });
    //添加下一页和末页 的提示
    ul.append(nextPageLi).append(lastPageLi);
    $("#paginationArea").empty();
    ul.appendTo("#paginationArea");

}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return "";
}

var storage = (function () {
    var storage = {};
    var store = localStorage;
    storage.isEmpty = function (key) {
        var val = store.getItem(key);
        if (val === null) {
            return true
        }
        return false
    }
    storage.set = function (key, value) {
        store.setItem(key, JSON.stringify(value));
    };
    storage.get = function (key, type) {
        var val = store.getItem(key);
        type = type || 'json';
        if (val && type === 'json') {
            return JSON.parse(val)
        }
        return val;
    };
    storage.remove = function (key) {
        store.removeItem(key);
    };
    storage.clear = function () {
        store.clear();
    };
    return storage
}())

$("#decoratorName").text(storage.get('user').dept.name + " - " + storage.get('user').name);