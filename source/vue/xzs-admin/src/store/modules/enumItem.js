// initial state
const state = {
  user: {
    sexEnum: [{ key: 1, value: 'Male' }, { key: 2, value: 'Female' }],
    statusEnum: [{ key: 1, value: 'Available' }, { key: 2, value: 'Unavailable' }],
    statusTag: [{ key: 1, value: 'success' }, { key: 2, value: 'danger' }],
    statusBtn: [{ key: 1, value: 'Abandon' }, { key: 2, value: 'Enable' }]
  },
  exam: {
    questionAnswer: {
      answer: {
        doRightTag: [{ key: true, value: 'success' }, { key: false, value: 'danger' }, { key: null, value: 'warning' }],
        doRightEnum: [{ key: true, value: 'Correct' }, { key: false, value: 'Incorrect' }],
        doCompletedTag: [{ key: false, value: 'info' }, { key: true, value: 'success' }]
      }
    }
  }
}

// getters
const getters = {
  enumFormat: (state) => (arrary, key) => {
    return format(arrary, key)
  }
}

// actions
const actions = {}

// mutations
const mutations = {}

const format = function (array, key) {
  for (let item of array) {
    if (item.key === key) {
      return item.value
    }
  }
  return null
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
