import React, { Component } from "react";

import UserService from "../services/user.service";
import EventBus from "../common/EventBus";
import articleService from "../services/articleService";
import AdminItemCard from "./components/admin_item";

export default class BoardAdmin extends Component {
  constructor(props) {
    super(props);

    this.state = {
      drafts: [],
    };
  }

  componentDidMount() {
    articleService.getAllDrafts().then(({ data }) => {
      this.setState({ drafts: data });
      console.log(data);
    });
  }

  removeItem = (index) => {
    let drafts = [...this.state.drafts];
    drafts.splice(index,1);
    this.setState({drafts});
  }

  render() {
    return (
      <div className="container">
        <header className="row container-fluid my-5">
          <h3>Drafts for review</h3>
        </header>
        <div className="container-fluid row">
          {this.state.drafts.map((d, i) => (
            <div key={i} className="col-md-6">
              <AdminItemCard draft={d} removeItem={()=>this.removeItem(i)} />
            </div>
          ))}
        </div>
      </div>
    );
  }
}
