import base from "./base"
import axios from "../request" // 导入axios实例
// import qs from "qs"

const virus = {
    // 获取全部数据
    getAllVirus() {
        return axios.get(`${base.virus}/virus/`);
    },
    // 按时间查询数据
    getVirusByTime(time) {
        return axios.get(`${base.virus}/virus/` + time)
    },
    // 获得每个国家数据
    getCountryData() {
        return axios.get(`${base.virus}/virus/country`);
    },
    // 获取每天的数据
    getVirusDivideByTime() {
        return axios.get(`${base.virus}/virus/day`);
    },
    // 获取每天累积的数据
    getAllVirusDivideByTime() {
        return axios.get(`${base.virus}/virus/dayAdded`);
    }
}

export default virus;