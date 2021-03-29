import base from "./base"
import axios from "../request" // 导入axios实例
// import qs from "qs"

const virus = {
    // 获取世界确诊数据
    getAllConfirmedVirusByTimestamp(timestamp = 1610380800) {
        return axios.get(`${base.virus}/virus/confirmed/` + timestamp);
    },
    // 获取世界治愈数据
    getAllRecoveredVirusByTimestamp(timestamp = 1610380800) {
        return axios.get(`${base.virus}/virus/recovered/` + timestamp);
    },
    // 获取世界死亡数据
    getAllDeathsVirusByTimestamp(timestamp = 1610380800) {
        return axios.get(`${base.virus}/virus/deaths/` + timestamp);
    },
    // 获取世界现存数据
    getAllActiveVirusByTimestamp(timestamp = 1610380800) {
        return axios.get(`${base.virus}/virus/active/` + timestamp);
    },
    // 获取世界死亡率数据
    getAllDeathsRatioByTimestamp(timestamp = 1610380800) {
        return axios.get(`${base.virus}/virus/deathsRatio/` + timestamp);
    },
    // 获取世界每日新增数据（确诊，死亡，治愈）
    getWorldNewIncreaseVirusData(country = "", province = "", city = "") {
        return axios.get(`${base.virus}/virus/newIncrease/` + country + "/" + province + "/" + city);
    },
    // 获取每日现存确诊、治愈、死亡数据
    getConfirmedRecoveredVirusData(country = "", province = "", city = "") {
        return axios.get(`${base.virus}/virus/confirmedRecovered/` + country + "/" + province + "/" + city);
    },
    // 获取每日累计确诊数据
    getCumulativeConfirmedVirusData(location = "world") {
        return axios.get(`${base.virus}/virus/cumulativeConfirmed/` + location);
    },
    // 获取全球每个国家确诊数据
    getWorldConfirmedVirusData(timestamp = 1610380800) {
        return axios.get(`${base.virus}/virus/confirmed/table/` + timestamp);
    },
    // 获取全球每个国家治愈数据
    getWorldRecoveredVirusData(timestamp = 1610380800) {
        return axios.get(`${base.virus}/virus/recovered/table/` + timestamp);
    },
    // 获取全球每个国家死亡数据
    getWorldDeathsVirusData(timestamp = 1610380800) {
        return axios.get(`${base.virus}/virus/deaths/table/` + timestamp);
    },
    // 获取全球每个国家现存数据
    getWorldActiveVirusData(timestamp = 1610380800) {
        return axios.get(`${base.virus}/virus/active/table/` + timestamp);
    },
    // 获取全球每个国家死亡率数据
    getWorldDeathsRatioData(timestamp = 1610380800) {
        return axios.get(`${base.virus}/virus/deathsRatio/table/` + timestamp);
    },
    // 获取国家每个省份确诊数据
    getCountryConfirmedVirusData(country, timestamp) {
        return axios.get(`${base.virus}/virus/confirmed/table/` + country + "/" + timestamp);
    },
    // 获取国家每个省份治愈数据
    getCountryRecoveredVirusData(country, timestamp) {
        return axios.get(`${base.virus}/virus/recovered/table/` + country + "/" + timestamp);
    },
    // 获取国家每个省份死亡数据
    getCountryDeathsVirusData(country, timestamp) {
        return axios.get(`${base.virus}/virus/deaths/table/` + country + "/" + timestamp);
    },
    // 获取国家每个省份现存数据
    getCountryActiveVirusData(country, timestamp) {
        return axios.get(`${base.virus}/virus/avtive/table/` + country + "/" + timestamp);
    },
    // 获取国家每个省份现存数据
    getCountryDeathsRatioData(country, timestamp) {
        return axios.get(`${base.virus}/virus/deathsRatio/table/` + country + "/" + timestamp);
    },
    // 获取省份每个城市确诊数据
    getProvinceConfirmedVirusData(country, province, timestamp) {
        return axios.get(`${base.virus}/virus/confirmed/table/` + country + "/" + province + "/" + timestamp);
    },
    // 获取省份每个城市治愈数据
    getProvinceRecoveredVirusData(country, province, timestamp) {
        return axios.get(`${base.virus}/virus/recovered/table/` + country + "/" + province + "/" + timestamp);
    },
    // 获取省份每个城市死亡数据
    getProvinceDeathsVirusData(country, province, timestamp) {
        return axios.get(`${base.virus}/virus/deaths/table/` + country + "/" + province + "/" + timestamp);
    },
    // 获取省份每个城市现存数据
    getProvinceActiveVirusData(country, province, timestamp) {
        return axios.get(`${base.virus}/virus/active/table/` + country + "/" + province + "/" + timestamp);
    },
    // 获取省份每个城市死亡率数据
    getProvinceDeathsRatioData(country, province, timestamp) {
        return axios.get(`${base.virus}/virus/deathsRatio/table/` + country + "/" + province + "/" + timestamp);
    },
    // 获取中国各省份确诊数据
    getChinaProvinceConfirmedVirusByTimestamp(timestamp = 1610380800) {
        return axios.get(`${base.virus}/virus/china/confirmed/` + timestamp);
    }
}

export default virus;