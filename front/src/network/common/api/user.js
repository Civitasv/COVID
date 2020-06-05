import base from "./base"
import axios from "../request" // 导入axios实例
// import qs from "qs"

const user = {
    validateLogin(username, password) {
        return axios.post(`${base.virus}/admin/login`, {
            username: username,
            password: password
        });
    },
    add() {
        return axios.put(`${base.virus}/admin/add`);
    }
}

export default user;