import http from "@/utils/request";

//请求首页的数据
export const getData = () => {
    return http.get('/home/getData');
}