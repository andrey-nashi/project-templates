import React, { Component } from 'react'
import { Header,Container} from 'semantic-ui-react'
import axios from 'axios'

import Settings from '../settings/Settings'

class PageHome extends Component {

   constructor(props) {
        super(props);
           this.state = {
                    data: "",
                }
   }

    fetch() {
        axios.get(Settings.API_OPENACCESS).then(response => {
        this.setState({data:response.data})})
    }

    componentDidMount() {
        this.fetch();
    }

    render () {
        return (
            <div className="home-content">
                <Container textAlign="left">
                <Header as="h1">Home</Header>
                {this.state.data}
                </Container>
            </div>
        )
    }
}

export default PageHome;