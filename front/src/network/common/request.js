import axios from "axios";
import router from "../../router/Router"

// 创建axios实例
var instance = axios.create({ timeout: 1000 * 120 });
// 设置post请求头
instance.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';


/** 
 * 请求拦截器 
 * 每次请求前，如果存在token则在请求头中携带token 
 */
instance.interceptors.request.use(
    config => {
        if (localStorage.JWT_TOKEN) {  // 判断是否存在token，如果存在的话，则每个http header都加上token
            config.headers = {
                'Content-Type': 'application/json; charset=utf-8',
                'Authorization': `${localStorage.JWT_TOKEN}`
            };
        }
        return config;
    },
    error => Promise.error(error))

// 响应拦截器
instance.interceptors.response.use(
    res => res.status === 200 ? Promise.resolve(res) : Promise.reject(res),
    // 请求失败
    error => {
        console.log("error")
        if (error.response) {
            switch (error.response.data["message"]) {
                case "401":
                    // 返回 401 清除token信息并跳转到登录页面
                    window.localStorage.removeItem('JWT_TOKEN')
                    alert("您的登录信息已过期，请重新登录！")
                    router.push({
                        path: 'login'
                    });
            }
            return Promise.reject(error.response);   // 返回接口返回的错误信息
        } else {
            // 处理断网的情况
            // eg:请求超时或断网时，更新state的network状态
            // network状态在app.vue中控制着一个全局的断网提示组件的显示隐藏
            // 关于断网组件中的刷新重新获取数据，会在断网组件中说明
            if (!window.navigator.onLine) {
                console.log("network error")
            } else {
                return Promise.reject(error);
            }
        }
    });

export default instance;