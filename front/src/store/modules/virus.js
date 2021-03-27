import virus from "../../network/common/api/virus"

const state = {
    confirmedGeoJson: {},
    recoveredGeoJson: {},
    deathsGeoJson: {},
    // 全球每日新增数据（确诊、治愈、死亡）
    newIncrease: [],
    // 获取每日现存确诊、治愈、死亡数据
    confirmedRecovered: [],
    // 全球每个国家确诊数据
    worldConfirmed: {},
    // 全球每个国家治愈数据
    worldRecovered: {},
    // 全球每个国家死亡数据
    worldDeaths: {},
    // 全球每个国家现存数据
    worldActive: {},
    // 某国家每个省份确诊数据
    countryConfirmed: {},
    // 某国家每个省份治愈数据
    countryRecovered: {},
    // 某国家每个省份死亡数据
    countryDeaths: {},
    // 某国家每个省份现存数据
    countryActive: {},
    // 某国家某省份某城市确诊数据
    provinceConfirmed: {},
    // 某国家某省份某城市治愈数据
    provinceRecovered: {},
    // 某国家某省份某城市死亡数据
    provinceDeaths: {},
    // 某国家某省份某城市现存数据
    provinceActive: {},
}

// getters
const getters = {

}

// actions
const actions = {
    // 获取世界确诊数据
    async getAllConfirmedVirusByTimestamp({ commit }, timestamp) {
        const res = await virus.getAllConfirmedVirusByTimestamp(timestamp);
        commit('setAllConfirmedVirusByTimestamp', { data: res.data, timestamp: timestamp });
    },
    // 获取世界治愈数据
    async getAllRecoveredVirusByTimestamp({ commit }, timestamp) {
        const res = await virus.getAllRecoveredVirusByTimestamp(timestamp);
        commit('setAllRecoveredVirusByTimestamp', { data: res.data, timestamp: timestamp });
    },
    // 获取世界死亡数据
    async getAllDeathsVirusByTimestamp({ commit }, timestamp) {
        const res = await virus.getAllDeathsVirusByTimestamp(timestamp);
        commit('setAllDeathsVirusByTimestamp', { data: res.data, timestamp: timestamp });
    },
    // 获取世界每日新增数据
    async getWorldNewIncreaseVirusData({ commit }) {
        const res = await virus.getWorldNewIncreaseVirusData();
        commit('setWorldNewIncreaseVirusData', res.data);
    },
    async getConfirmedRecoveredVirusData({ commit }) {
        const res = await virus.getConfirmedRecoveredVirusData();
        commit('setConfirmedRecoveredVirusData', res.data);
    },
    async getWorldConfirmedVirusData({ commit }, timestamp) {
        const res = await virus.getWorldConfirmedVirusData(timestamp);
        commit('setWorldConfirmedVirusData', { data: res.data, timestamp: timestamp });
    },
    async getWorldRecoveredVirusData({ commit }, timestamp) {
        const res = await virus.getWorldRecoveredVirusData(timestamp);
        commit('setWorldRecoveredVirusData', { data: res.data, timestamp: timestamp });
    },
    async getWorldDeathsVirusData({ commit }, timestamp) {
        const res = await virus.getWorldDeathsVirusData(timestamp);
        commit('setWorldDeathsVirusData', { data: res.data, timestamp: timestamp });
    },
    async getWorldActiveVirusData({ commit }, timestamp) {
        const res = await virus.getWorldActiveVirusData(timestamp);
        commit('setWorldActiveVirusData', { data: res.data, timestamp: timestamp });
    },
    async getCountryConfirmedVirusData({ commit }, { country, timestamp }) {
        const res = await virus.getCountryConfirmedVirusData(country, timestamp);
        commit('setCountryConfirmedVirusData', { data: res.data, country: country, timestamp: timestamp });
    },
    async getCountryRecoveredVirusData({ commit }, { country, timestamp }) {
        const res = await virus.getCountryRecoveredVirusData(country, timestamp);
        commit('setCountryRecoveredVirusData', { data: res.data, country: country, timestamp: timestamp });
    },
    async getCountryDeathsVirusData({ commit }, { country, timestamp }) {
        const res = await virus.getCountryDeathsVirusData(country, timestamp);
        commit('setCountryDeathsVirusData', { data: res.data, country: country, timestamp: timestamp });
    },
    async getCountryActiveVirusData({ commit }, { country, timestamp }) {
        const res = await virus.getCountryActiveVirusData(country, timestamp);
        commit('setCountryActiveVirusData', { data: res.data, country: country, timestamp: timestamp });
    },
    async getProvinceConfirmedVirusData({ commit }, { country, province, timestamp }) {
        const res = await virus.getProvinceConfirmedVirusData(country, province, timestamp);
        commit('setProvinceConfirmedVirusData', { data: res.data, country: country, province, timestamp: timestamp });
    },
    async getProvinceRecoveredVirusData({ commit }, { country, province, timestamp }) {
        const res = await virus.getProvinceRecoveredVirusData(country, province, timestamp);
        commit('setProvinceRecoveredVirusData', { data: res.data, country: country, province, timestamp: timestamp });
    },
    async getProvinceDeathsVirusData({ commit }, { country, province, timestamp }) {
        const res = await virus.getProvinceDeathsVirusData(country, province, timestamp);
        commit('setProvinceDeathsVirusData', { data: res.data, country: country, province, timestamp: timestamp });
    },
    async getProvinceActiveVirusData({ commit }, { country, province, timestamp }) {
        const res = await virus.getProvinceActiveVirusData(country, province, timestamp);
        commit('setProvinceActiveVirusData', { data: res.data, country: country, province, timestamp: timestamp });
    }
}

// mutations
const mutations = {
    setAllConfirmedVirusByTimestamp(state, res) {
        state.confirmedGeoJson[res.timestamp] = res.data;
    },
    setAllRecoveredVirusByTimestamp(state, res) {
        state.recoveredGeoJson[res.timestamp] = res.data;
    },
    setAllDeathsVirusByTimestamp(state, res) {
        state.deathsGeoJson[res.timestamp] = res.data;
    },
    setWorldNewIncreaseVirusData(state, worldNewIncreaseVirusData) {
        state.newIncrease = worldNewIncreaseVirusData;
    },
    setConfirmedRecoveredVirusData(state, confirmedRecovered) {
        state.confirmedRecovered = confirmedRecovered;
    },
    setWorldConfirmedVirusData(state, res) {
        state.worldConfirmed[res.timestamp] = res.data;
    },
    setWorldRecoveredVirusData(state, res) {
        state.worldRecovered[res.timestamp] = res.data;
    },
    setWorldDeathsVirusData(state, res) {
        state.worldDeaths[res.timestamp] = res.data;
    },
    setWorldActiveVirusData(state, res) {
        state.worldActive[res.timestamp] = res.data;
    },
    setCountryConfirmedVirusData(state, res) {
        if (state.countryConfirmed[res.country]) {
            const country = state.countryConfirmed[res.country];
            country[res.timestamp] = res.data;
        } else {
            state.countryConfirmed[res.country] = new Object();
            const country = state.countryConfirmed[res.country];
            country[res.timestamp] = res.data;
        }

    },
    setCountryRecoveredVirusData(state, res) {
        if (state.countryRecovered[res.country]) {
            const country = state.countryRecovered[res.country];
            country[res.timestamp] = res.data;
        } else {
            state.countryRecovered[res.country] = new Object();
            const country = state.countryRecovered[res.country];
            country[res.timestamp] = res.data;
        }
    },
    setCountryDeathsVirusData(state, res) {
        if (state.countryDeaths[res.country]) {
            const country = state.countryDeaths[res.country];
            country[res.timestamp] = res.data;
        } else {
            state.countryDeaths[res.country] = new Object();
            const country = state.countryDeaths[res.country];
            country[res.timestamp] = res.data;
        }
    },
    setCountryActiveVirusData(state, res) {
        if (state.countryActive[res.country]) {
            const country = state.countryActive[res.country];
            country[res.timestamp] = res.data;
        } else {
            state.countryActive[res.country] = new Object();
            const country = state.countryActive[res.country];
            country[res.timestamp] = res.data;
        }
    },
    setProvinceConfirmedVirusData(state, res) {
        if (state.provinceConfirmed[res.country]) {
            const country = state.provinceConfirmed[res.country];
            if (country[res.province]) {
                const province = country[res.province];
                province[res.timestamp] = res.data;
            } else {
                country[res.province] = new Object();
                const province = country[res.province];
                province[res.timestamp] = res.data;
            }
        } else {
            state.provinceConfirmed[res.country] = new Object();
            const country = state.provinceConfirmed[res.country];
            if (country[res.province]) {
                const province = country[res.province];
                province[res.timestamp] = res.data;
            } else {
                country[res.province] = new Object();
                const province = country[res.province];
                province[res.timestamp] = res.data;
            }
        }
    },
    setProvinceRecoveredVirusData(state, res) {
        if (state.provinceRecovered[res.country]) {
            const country = state.provinceRecovered[res.country];
            if (country[res.province]) {
                const province = country[res.province];
                province[res.timestamp] = res.data;
            } else {
                country[res.province] = new Object();
                const province = country[res.province];
                province[res.timestamp] = res.data;
            }
        } else {
            state.provinceRecovered[res.country] = new Object();
            const country = state.provinceRecovered[res.country];
            if (country[res.province]) {
                const province = country[res.province];
                province[res.timestamp] = res.data;
            } else {
                country[res.province] = new Object();
                const province = country[res.province];
                province[res.timestamp] = res.data;
            }
        }
    },
    setProvinceDeathsVirusData(state, res) {
        if (state.provinceDeaths[res.country]) {
            const country = state.provinceDeaths[res.country];
            if (country[res.province]) {
                const province = country[res.province];
                province[res.timestamp] = res.data;
            } else {
                country[res.province] = new Object();
                const province = country[res.province];
                province[res.timestamp] = res.data;
            }
        } else {
            state.provinceDeaths[res.country] = new Object();
            const country = state.provinceDeaths[res.country];
            if (country[res.province]) {
                const province = country[res.province];
                province[res.timestamp] = res.data;
            } else {
                country[res.province] = new Object();
                const province = country[res.province];
                province[res.timestamp] = res.data;
            }
        }
    },
    setProvinceActiveVirusData(state, res) {
        if (state.provinceActive[res.country]) {
            const country = state.provinceActive[res.country];
            if (country[res.province]) {
                const province = country[res.province];
                province[res.timestamp] = res.data;
            } else {
                country[res.province] = new Object();
                const province = country[res.province];
                province[res.timestamp] = res.data;
            }
        } else {
            state.provinceActive[res.country] = new Object();
            const country = state.provinceActive[res.country];
            if (country[res.province]) {
                const province = country[res.province];
                province[res.timestamp] = res.data;
            } else {
                country[res.province] = new Object();
                const province = country[res.province];
                province[res.timestamp] = res.data;
            }
        }
    },
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}