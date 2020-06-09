import base from "./base"
import axios from "../request" // 导入axios实例
// import qs from "qs"

const virus = {
    // 获取全部数据 可静态 热力图 聚合图
    getAllVirus() {
        return axios.get(`${base.virus}/virus/`);
    },
    // 获取全部中国数据 可静态 热力图 聚合图
    getAllChinaVirus() {
        return axios.get(`${base.virus}/virus/china`);
    },
    // 按时间查询数据 管理
    getVirusByTime(time) {
        return axios.get(`${base.virus}/virus/` + time)
    },
    // 按地点模糊查询数据 管理
    getVirusByAddressAndTime(time, address) {
        return axios.get(`${base.virus}/virus/address`, {
            params: { 'time': time, 'address': address }
        })
    },
    // 获得每个国家数据 可静态 
    getCountryData() {
        return axios.get(`${base.virus}/virus/country`);
    },
    // 获得每个省份数据 可静态 
    getProvinceData() {
        return axios.get(`${base.virus}/virus/china/province`);
    },
    // 获取每天的数据 可静态 echarts图
    getVirusDivideByTime() {
        return axios.get(`${base.virus}/virus/day`);
    },
    // 获取中国每天的数据 可静态 echarts图
    getChinaVirusDivideByTime() {
        return axios.get(`${base.virus}/virus/china/day`);
    },
    // 获取累积的数据 可静态 echarts图
    getAllVirusDivideByTime() {
        return axios.get(`${base.virus}/virus/dayAdded`);
    },
    // 获取中国累积的数据 可静态 echarts图
    getAllChinaVirusDivideByTime() {
        return axios.get(`${base.virus}/virus/china/dayAdded`);
    },
    // 更新数据
    updateVirus(virus) {
        return axios.put(`${base.virus}/admin/update`, {
            id: virus.id,
            publicTime: virus.public_time,
            newDiagnosis: virus.confirmed,
            newRecovery: virus.recovery,
            newDeath: virus.deaths,
            country: virus.country,
            province: virus.province,
            city: virus.city,
            description: virus.description,
            image: virus.base64
        })
    },
    // 删除数据
    deleteVirus(id) {
        return axios.delete(`${base.virus}/admin/delete`, {
            params: { 'id': id }
        })
    },
    // 添加数据
    addVirus(virus) {
        return axios.post(`${base.virus}/admin/add`, {
            publicTime: virus.public_time,
            lat: virus.lat,
            lng: virus.lng,
            newDiagnosis: virus.confirmed,
            newRecovery: virus.recovery,
            newDeath: virus.deaths,
            country: virus.country,
            province: virus.province,
            city: virus.city,
            description: virus.description,
            image: virus.base64
        })
    },
}

export default virus;