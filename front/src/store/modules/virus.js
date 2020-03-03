import virus from "../../network/common/api/virus"

const state = {
    geoJSON: ""
}

// getters
const getters = {

}

// actions
const actions = {
    getAllVirus({ commit }) {
        virus.getAllVirus().then(
            function (res) {
                commit('setGeoJSON', res.data);
            }
        )
    }
}

// mutatios
const mutations = {
    setGeoJSON(state, geoJson) {
        state.geoJSON = geoJson;
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}