import virus from "../../network/common/api/virus"

const state = {
    // 全部数据
    allVirus: "",
    // 按时间分组的数据
    countryData: ""
}

// getters
const getters = {

}

// actions
const actions = {
    async getAllVirus({ commit }) {
        await virus.getAllVirus().then(
            function (res) {
                commit('setGeoJSON', res.data);
            }
        )
    },
    async getCountryData({ commit }) {
        await virus.getCountryData().then(
            function (res) {
                commit('setCountryData', res.data);
            }
        )
    }
}

// mutatios
const mutations = {
    setGeoJSON(state, geoJson) {
        state.geoJSON = geoJson;
    },
    setCountryData(state, countryData) {
        state.countryData = countryData;
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}