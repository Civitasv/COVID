import base from "./base"
import axios from "../request" // 导入axios实例
// import qs from "qs"

const user = {
    login(username, password) {
        return axios.post(`${base.virus}/admin/login`, {
            username: username,
            password: password
        });
    },
    regist(username, password) {
        return axios.post(`${base.virus}/admin/regist`, {
            username: username,
            password: password
        });
    },
    checkUsername(username) {
        return axios.post(`${base.virus}/admin/checkUsername`, {
            username: username
        });
    }
}

export default user;