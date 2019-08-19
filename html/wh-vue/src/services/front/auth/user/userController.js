/** 处理页面跳转和页面参数传递 **/
/* eslint-disable */
import {methods} from './userService'

const name = 'user';

const data = function () {
  return {
    data:[{"id":10000,"username":"user-0","sex":"女","city":"城市-0","sign":"签名-0","experience":255,"logins":24,"wealth":82830700,"classify":"作家","score":57},{"id":10001,"username":"user-1","sex":"男","city":"城市-1","sign":"签名-1","experience":884,"logins":58,"wealth":64928690,"classify":"词人","score":27},{"id":10002,"username":"user-2","sex":"女","city":"城市-2","sign":"签名-2","experience":650,"logins":77,"wealth":6298078,"classify":"酱油","score":31},{"id":10003,"username":"user-3","sex":"女","city":"城市-3","sign":"签名-3","experience":362,"logins":157,"wealth":37117017,"classify":"诗人","score":68},{"id":10004,"username":"user-4","sex":"男","city":"城市-4","sign":"签名-4","experience":807,"logins":51,"wealth":76263262,"classify":"作家","score":6},{"id":10005,"username":"user-5","sex":"女","city":"城市-5","sign":"签名-5","experience":173,"logins":68,"wealth":60344147,"classify":"作家","score":87},{"id":10006,"username":"user-6","sex":"女","city":"城市-6","sign":"签名-6","experience":982,"logins":37,"wealth":57768166,"classify":"作家","score":34},{"id":10007,"username":"user-7","sex":"男","city":"城市-7","sign":"签名-7","experience":727,"logins":150,"wealth":82030578,"classify":"作家","score":28},{"id":10008,"username":"user-8","sex":"男","city":"城市-8","sign":"签名-8","experience":951,"logins":133,"wealth":16503371,"classify":"词人","score":14},{"id":10009,"username":"user-9","sex":"女","city":"城市-9","sign":"签名-9","experience":484,"logins":25,"wealth":86801934,"classify":"词人","score":75}]
  };
};


const computed = {

};

const components = {
};


const watch = {

};

function created() {

}

function mounted() {
  let that = this;
  layui.use('table', function(){
    let table = layui.table;
    table.render({
      elem: '#userTable'
      //,url:'/demo/table/user/'
      ,data:that.data
      ,page: true //开启分页
      ,limits:[5,10,20,30,40,50,60,70,80,90]
      ,loading:true//分页加载参数
      ,totalRow:false//合计行
      ,toolbar: '#userToolbar'
      ,defaultToolbar: ['filter', 'print', 'exports']
      ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
      ,cols: [[
        {type: 'checkbox', fixed: 'left'}
        ,{field:'id', width:80, title: 'ID'}
        ,{field:'username', width:80, title: '用户名'}
        ,{field:'sex', width:80, title: '性别'}
        ,{field:'city', width:80, title: '城市'}
        ,{field:'sign', title: '签名', width: '10%', minWidth: 100} //minWidth：局部定义当前单元格的最小宽度，layui 2.2.1 新增
        ,{field:'experience', title: '积分'}
        ,{field:'score', title: '评分'}
        ,{field:'classify', title: '职业'}
        ,{field:'wealth', width:137, title: '财富'}
        ,{fixed: 'right', title:'操作', toolbar: '#userColBar', width:150}
      ]]
      ,response: {//服务器返回数据进行预加工，字段名称匹配
        statusName: 'status' //规定数据状态的字段名称，默认：code
        ,statusCode: 200 //规定成功的状态码，默认：0
        ,msgName: 'hint' //规定状态信息的字段名称，默认：msg
        ,countName: 'total' //规定数据总数的字段名称，默认：count
        ,dataName: 'rows' //规定数据列表的字段名称，默认：data
      }
      ,done: function(res, curr, count){//渲染完成后回调
        //如果是异步请求数据方式，res即为你接口返回的信息。
        //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
        console.log(res);

        //得到当前页码
        console.log(curr);

        //得到数据总量
        console.log(count);
      }
      ,text: {//当未查询到数据时显示信息
        none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
      }
    });
    //头工具栏事件
    table.on('toolbar(userTableFilter)', function(obj){
      var checkStatus = table.checkStatus(obj.config.id);
      switch(obj.event){
        case 'getCheckData':
          var data = checkStatus.data;
          layer.alert(JSON.stringify(data));
          break;
        case 'getCheckLength':
          var data = checkStatus.data;
          layer.msg('选中了：'+ data.length + ' 个');
          break;
        case 'isAll':
          layer.msg(checkStatus.isAll ? '全选': '未全选');
          break;
      };
    });

    //监听行工具事件
    table.on('tool(userTableFilter)', function(obj){
      var data = obj.data;
      //console.log(obj)
      if(obj.event === 'del'){
        layer.confirm('真的删除行么', function(index){
          obj.del();
          layer.close(index);
        });
      } else if(obj.event === 'edit'){
        layer.prompt({
          formType: 2
          ,value: data.email
        }, function(value, index){
          obj.update({
            email: value
          });
          layer.close(index);
        });
      }
    });
  });
  layui.use(['form', 'layedit', 'laydate'], function(){
    let form = layui.form
      ,layer = layui.layer
      ,layedit = layui.layedit
      ,laydate = layui.laydate;

    //日期
    laydate.render({
      elem: '#date'
    });
    laydate.render({
      elem: '#date1'
    });

    //创建一个编辑器
    var editIndex = layedit.build('LAY_demo_editor');

    //自定义验证规则
    form.verify({
      title: function(value){
        if(value.length < 5){
          return '标题至少得5个字符啊';
        }
      }
      ,pass: [
        /^[\S]{6,12}$/
        ,'密码必须6到12位，且不能出现空格'
      ]
      ,content: function(value){
        layedit.sync(editIndex);
      }
    });

    //监听指定开关
    form.on('switch(switchTest)', function(data){
      layer.msg('开关checked：'+ (this.checked ? 'true' : 'false'), {
        offset: '6px'
      });
      layer.tips('温馨提示：请注意开关状态的文字可以随意定义，而不仅仅是ON|OFF', data.othis)
    });

    form.render();
  });
}

export default {
  name: name,

  data: data,

  components: components,

  created: created,

  computed: computed,

  watch: watch,

  methods: methods,

  mounted: mounted
};
