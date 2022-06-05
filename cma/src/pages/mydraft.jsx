import React, { Component } from "react";
import articleService from "../services/articleService";
import userService from "../services/user.service";
import KEY from "../KEY.json";
import Itemcard from "./components/item_card";

class MyDraft extends Component {
  state = {
    drafts: [],
  };

  componentDidMount() {
    this.init();
  }

  async init() {
    const {data} = await articleService.getMyDrafts();
    console.log(data);
    this.setState({drafts:data});
  }

  deleteItem = (index)=>{
    let {drafts} = this.state;
    drafts.splice(index,1);
    this.setState({drafts});
  }

  render() {

    return (
      <div>
        <section className="py-20 bg-light overflow-hidden">
          <div className="container">
            <div className="p-8 p-lg-20 bg-white">
              <h2 className="mb-8 mb-md-20">Your articles under review</h2>

              {this.state.drafts.length == 0 ? (
                <h3>No articles found</h3>
              ) : (
                <div className="row align-items-center">
                  {this.state.drafts.map((e,i) => (
                    <div className="col-12 col-md-6" key={e["id"]}>
                      <Itemcard article={e} deleteItem={()=>this.deleteItem(i)} />
                    </div>
                  ))}
                </div>
              )}
            </div>
          </div>
        </section>
      </div>
    );
  }
}

export default MyDraft;
