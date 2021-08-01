// initial state
const state = {
  user: {
    sexEnum: [{ key: 1, value: 'Male' }, { key: 2, value: 'Female' }]
  },
  exam: {
    examPaperAnswer: {
      statusEnum: [{ key: 1, value: 'To be marked' }, { key: 2, value: 'Complete' }],
      statusTag: [{ key: 1, value: 'warning' }, { key: 2, value: 'success' }]
    },
    question: {
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
