/* eslint-disable */
layui.use('element', function () {
});
var menu = [{"id":"4028820566af216a0166af216ad30000","name":"系统管理","url":"#","ico":"","submenu":[{"id":"4028820566af22890166af22898b0000","name":"用户管理","url":"#","submenu":[{"id":"4028820566af29ea0166af29eaab0013","name":"机构管理","url":"/org/orgManage"},{"id":"4028820566af29ea0166af29eaab0012","name":"用户管理","url":"/user/userManage"},{"id":"4028820566af29ea0166af29eaab0011","name":"资源菜单管理","url":"/resource/resourceManage"},{"id":"4028820566af26360166af2636200013","name":"角色管理","url":"/role/roleManage"}]},{"id":"4028820566c437260166c43817c30002","name":"征信用户管理","url":"#","submenu":[{"id":"4028820566c437260166c44164730018","name":"征信用户维护","url":"/creditUser/index"},{"id":"4028820566c437260166c441de3f001b","name":"征信用户映射","url":"/usermap/usermapManage"}]},{"id":"4028820566c437260166c43899120005","name":"参数管理","url":"#","submenu":[{"id":"4028820566c437260166c442e470001e","name":"系统参数管理","url":"/sysparameter/sysparameterManage"},{"id":"4028820566c437260166c443f9b80021","name":"信用报告版式与原因","url":"/qrToFormat/index"},{"id":"CMS001304","name":"工作日管理","url":"/workday/workdayManage"}]},{"id":"4028820566c437260166c43911dc0008","name":"字典管理","url":"#","submenu":[{"id":"4028820566c437260166c44573620027","name":"字典类型管理","url":"/dictype/dictypeManage"},{"id":"4028820566c437260166c444c34d0024","name":"字典管理","url":"/dictionary/dictionaryManage"}]},{"id":"4028820566c437260166c4397bf6000b","name":"日志管理","url":"#","submenu":[{"id":"4028820566c437260166c445f8a6002a","name":"核心日志管理","url":"/corelogs/corelogsManage"},{"id":"4028820566c437260166c4466e74002d","name":"操作日志管理","url":"/syslogs/syslogsManage"}]},{"id":"40288205677266f10167726988cd0000","name":"定时任务","url":"#","submenu":[{"id":"40288205677266f10167726b46c10005","name":"定时任务","url":"/job/jobManage"}]},{"id":"4028820566c437260166c439c944000e","name":"个人信息","url":"#","submenu":[{"id":"4028820566c437260166c446fdf20030","name":"个人信息","url":"/baseinfo/baseinfoManage"}]}]},{"id":"4028820566af237c0166af237ce90000","name":"征信查询","url":"#","ico":"","submenu":[{"id":"4028820566af24500166af24508d0000","name":"个人征信","url":"#","submenu":[{"id":"4028820566af26360166af2636200010","name":"信用报告","url":"/personalCreditQuery/personalCreditQueryManage"},{"id":"4028820566af26360166af26361f000e","name":"授权","url":"/personalAuthorize/personalAuthorizeManage"},{"id":"4028820566af26360166af26361f0006","name":"复核管理","url":"/personalRecheck/personalRecheckManage"},{"id":"4028820566af26360166af26361f0009","name":"补录授权","url":"/personalReplement/personalReplementManage"},{"id":"4028820566af26360166af26361f0008","name":"批量信用报告","url":"/batchImportRecord/index"},{"id":"4028820566af26360166af26361f0005","name":"查询记录管理","url":"/personalDownload/personalDownloadManage"},{"id":"402882aa67baf52b0167bb01434f0003","name":"接口查询","url":"/connectorPersonal/index"},{"id":"602e1dbb936042cfab3b94ca1ae459b9","name":"信用报告打印","url":"/pprint/index"}]},{"id":"4028820566af24bb0166af24bb060000","name":"企业征信","url":"#","submenu":[{"id":"4028820566af26360166af26361f0004","name":"信用报告","url":"/businessCreditQuery/businessCreditQueryManage"},{"id":"4028820566af26360166af26361f0002","name":"授权","url":"/businessAuthorize/businessAuthorizeManage"},{"id":"4028820566af26360166af26361f0003","name":"复核管理","url":"/businessRecheck/businessRecheckManage"},{"id":"4028820566af26360166af26361f0001","name":"补录授权","url":"/businessReplement/businessReplementManage"},{"id":"4028820566af29ea0166af29eaab0009","name":"批量信用报告","url":"/businessImportMsgInfo/index"},{"id":"4028820566af26360166af26361f0000","name":"查询记录管理","url":"/businessDownload/businessDownloadManage"},{"id":"402882aa67baf52b0167bb038335000c","name":"接口查询","url":"/connectorBusiness/index"},{"id":"595a54c734e548948e7c146f4d6f78c9","name":"信用报告打印","url":"/eprint/index"}]}]},{"id":"4028820566af25710166af2571570000","name":"查询监测","url":"#","ico":"","submenu":[{"id":"402882aa67625d4e01676263874d0004","name":"监测参数管理","url":"#","submenu":[{"id":"402882aa67625d4e0167626442ec0007","name":"监测参数查询","url":"/creditParameter/index"}]},{"id":"40288205671608990167160899890000","name":"预警信息管理","url":"#","submenu":[{"id":"402882056716089901671609d38e0003","name":"预警信息查询","url":"/queryWarn/queryWarnManage"}]},{"id":"40288205671bbc7701671bbc77f60000","name":"违规统计管理","url":"#","submenu":[{"id":"40288205671bbfd101671bbfd10c0000","name":"违规统计查询","url":"/queryWarn/queryIllegalManage"}]}]},{"id":"4028820566af25a70166af25a7e60000","name":"统计分析","url":"#","ico":"","submenu":[{"id":"402882aa670ffbf501670ffbf5c10000","name":"个人统计分析","url":"#","submenu":[{"id":"402882aa670ffbf501670ffc6e7a0003","name":"个人统计分析","url":"/personalAnalysis/index"},{"id":"8165aa4b0c0b4a42962d88dff17d2ddd","name":"个人打印统计分析","url":"/personalAnalysis/print"}]}]}];
export function renderFirstPage() {
    var top_menu_str = "";
    menu.forEach(function(data, index, self) {
        var activeClass = "";
        if(index == 0){
            activeClass = "layui-this";
        }
        top_menu_str += '<li class="layui-nav-item '+activeClass+'"><a href="#" onclick="jumpTo(\''+data.id+'\')">'+data.name+'</a></li>';
    });
    $("#top_menu").html(top_menu_str);
    $(".layui-this a").click();
}
renderFirstPage();

export function jumpTo(id){
    for (let i = 0; i < menu.length; i++){
        let data = menu[i];
        if(id == data.id){
            renderSubMenuHTML(data.submenu);
            break;
        }
    }
}

export function renderSubMenuHTML(secondMenu){
    let sub_menu = '<ul class="layui-nav layui-nav-tree" lay-filter="test">';
    secondMenu.forEach(function(data, index, self){
        let isFirst="";
        if(index == 0){
            isFirst = " layui-nav-itemed";
        }
        sub_menu +=
            '<li class="layui-nav-item'+isFirst+'">' +
            '<a class="" href="javascript:;">'+data.name+'</a>' +
            '<dl class="layui-nav-child">';
        data.submenu.forEach(function (data, index1, self) {
            let activeClass = '';
            if (index == 0 && index1 == 0){
               // activeClass = 'class="layui-this"';
            }
            sub_menu +=
                '<router-link tag="dd" to="'+data.url+'" exact-active-class="layui-this" exact '+activeClass+'><a href="#">'+data.name+'</a></router-link>';
        });
        sub_menu+='</dl>'+  '</li>';
    });
    sub_menu+='</ul>';
    $("#sub_menu").html(sub_menu);
    layui.element.render('nav', 'test')
}

