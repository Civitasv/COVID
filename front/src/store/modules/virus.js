import virus from "../../network/common/api/virus"

const state = {
    // 全部数据
    allVirus: "",
    allChinaVirus: "",
    // 按国家分组的数据
    countryData: [],
    // 按省份分组的数据
    provinceData: [],
    // 按时间分组的数据
    virusDivideByTime: [],
    // 按时间分组的中国数据
    chinaVirusDivideByTime: [],
    // 获取累积的数据
    allVirusDivideByTime: [],
    // 获取中国累积的数据
    allChinaVirusDivideByTime: [],
    // 最近一天的所有数据
    allLatestVirus: ""
}

// getters
const getters = {

}

// actions
const actions = {
    async getAllVirus({ commit }) {
        await virus.getAllVirus().then(
            function (res) {
                commit('setAllVirus', res.data);
            }
        )
    },
    async getAllChinaVirus({ commit }) {
        await virus.getAllChinaVirus().then(
            function (res) {
                commit('setAllChinaVirus', res.data);
            }
        )
    },
    async getCountryData({ commit }) {
        await virus.getCountryData().then(
            function (res) {
                commit('setCountryData', res.data);
            }
        )
    },
    async getProvinceData({ commit }) {
        await virus.getProvinceData().then(
            function (res) {
                commit('setProvinceData', res.data);
            }
        )
    },
    async getVirusDivideByTime({ commit }) {
        await virus.getVirusDivideByTime().then(
            function (res) {
                commit('setVirusDivideByTime', res.data);
            }
        )
    },
    async getChinaVirusDivideByTime({ commit }) {
        await virus.getChinaVirusDivideByTime().then(
            function (res) {
                commit('setChinaVirusDivideByTime', res.data);
            }
        )
    },
    async getAllVirusDivideByTime({ commit }) {
        await virus.getAllVirusDivideByTime().then(
            function (res) {
                commit('setAllVirusDivideByTime', res.data);
            }
        )
    },
    async getAllChinaVirusDivideByTime({ commit }) {
        await virus.getAllChinaVirusDivideByTime().then(
            function (res) {
                commit('setAllChinaVirusDivideByTime', res.data);
            }
        )
    },
    async getAllLatestVirus({ commit }) {
        await virus.getAllLatestVirus().then(
            function (res) {
                commit("setAllLatestVirus", res.data);
            }
        )
    }
}

// mutations
const mutations = {
    setAllVirus(state, allVirus) {
        state.allVirus = allVirus;
    },
    setAllChinaVirus(state, allChinaVirus) {
        state.allChinaVirus = allChinaVirus;
    },
    setCountryData(state, countryData) {
        state.countryData = countryData;
    },
    setProvinceData(state, provinceData) {
        state.provinceData = provinceData;
    },
    setVirusDivideByTime(state, virusDivideByTime) {
        state.virusDivideByTime = virusDivideByTime;
    },
    setChinaVirusDivideByTime(state, chinaVirusDivideByTime) {
        state.chinaVirusDivideByTime = chinaVirusDivideByTime;
    },
    setAllVirusDivideByTime(state, allVirusDivideByTime) {
        state.allVirusDivideByTime = allVirusDivideByTime;
    },
    setAllChinaVirusDivideByTime(state, allChinaVirusDivideByTime) {
        state.allChinaVirusDivideByTime = allChinaVirusDivideByTime;
    },
    setAllLatestVirus(state, allLatestVirus) {
        state.allLatestVirus = allLatestVirus;
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}