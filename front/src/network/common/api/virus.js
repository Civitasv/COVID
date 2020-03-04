import base from "./base"
import axios from "../request" // 导入axios实例
// import qs from "qs"

const virus = {
    // 数据
    getAllVirus() {
        return axios.get(`${base.virus}/getAllVirus`);
    }
    // 上传文件
    /*uploadData(file) {
        return axios.post(`${base.virus}/upload`, file)
    }*/
}

export default virus;