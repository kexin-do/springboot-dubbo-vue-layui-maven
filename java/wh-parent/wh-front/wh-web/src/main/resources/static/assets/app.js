
const app = new Vue({
    router:new VueRouter({
        linkExactActiveClass:"layui-this",
        /*linkActiveClass:"layui-this",*/
        routes:[
            { path: '/org/orgManage', component: { template: "../../templates/assets/index.vue" } },
            { path: '/user/userManage', component: { template: '<div>bar</div>' } }
        ]
    })
}).$mount('#app');