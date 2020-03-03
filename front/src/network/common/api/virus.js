import base from "./base"
import axios from "../request" // 导入axios实例
// import qs from "qs"

const virus = {
    // 数据
    getAllVirus() {
        return axios.get(`${base.blog}/getAllVirus`);
    }
    // 其它...
}

export default virus;