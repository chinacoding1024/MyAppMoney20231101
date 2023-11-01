import axios from "axios";
import store from '../store'
import { getToken } from '@/utils/auth'
const http = axios.create({
    baseURL: 'http://localhost:9110/',//通用请求的地址 '/api',//
    timeout: 100000,//超时时间,10000毫秒，10秒

});
// 添加请求拦截器
http.interceptors.request.use(function (config) {

    console.log("store.getters.token " + store.getters.token)
    console.log("getToken() " + getToken())
    // 在发送请求之前做些什么  
    if (store.getters.token) { 
        config.headers['Authorization'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
      }
    

    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 添加响应拦截器
http.interceptors.response.use(function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    return response.data;
}, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    return Promise.reject(error);
});
export default http