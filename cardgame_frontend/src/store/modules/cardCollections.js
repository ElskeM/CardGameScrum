import axios from 'axios'


const state = {
    fullDeck: [{
        id: 1,
        title: "Kort 1",
        description: "Ett jättebra kort",
        score: 1000,
        category: "Djur och natur"
    },
    {
        id: 2,
        title: "Kort 2",
        description: "Ett dåligt kort",
        score: 500,
        category: "Djur och natur"
    },
    {
        id: 3,
        title: "Kort 3",
        description: "Ett hyfsat kort",
        score: 750,
        category: "Djur och natur"
    },
    {
        id: 4,
        title: "Kort 4",
        description: "Ett ganska dålig kort",
        score: 100,
        category: "Samhälle"
    },
    {
        id: 5,
        title: "Kort 5",
        description: "Ett ganska bra kort",
        score: 800,
        category: "Samhälle"
    },
    {
        id: 6,
        title: "Kort 6",
        description: "Ett mediokert kort",
        score: 250,
        category: "Samhälle"
    },
    {
        id: 7,
        title: "Kort 7",
        description: "Ett jävla superkort",
        score: 5000,
        category: "Samhälle"
    },
    {
        id: 8,
        title: "Kort 8",
        description: "Ett uselt kort",
        score: 150,
        category: "Historia"
    },
    {
        id: 10,
        title: "Kort 10",
        description: "Ett rätt bra kort",
        score: 550,
        category: "Historia"
    },
    {
        id: 11,
        title: "Kort 11",
        description: "Ett rakt av miserabelt kort",
        score: 50,
        category: "Historia"
    },
    {
        id: 12,
        title: "Kort 12",
        description: "Ett kvalitetskort",
        score: 2000,
        category: "Människan"
    } 
    ]
}

const getters = {
    wholeCollection: (state) => state.fullDeck,
   
    
}

const actions = {
    async fetchFullDeck() {
        const response = await axios.get('http://localhost:8080/allCards')
        console.log(response.data)
    }


}

const mutations = {

}

export default {
    state,
    getters,
    actions,
    mutations
}