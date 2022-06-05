import axios from "axios";
import authHeader from "./auth-header";

import config from "../config.json";
import userService from "./user.service";

const API_URL = config.base_url_articles;

class ArticleService {
  getPublicContent(pageNo = 1) {
    return axios.get(API_URL, {
      params: {
        pageNo: pageNo,
      },
      headers: authHeader(),
    });
  }

  searchPublicContent(text, pageNo = 1) {
    return axios.get(API_URL, {
      params: {
        pageNo: pageNo,
        search: text,
      },
      headers: authHeader(),
    });
  }

  writeArticle(text, id, originalArticleId) {
    const user = userService.getCurrentUser();

    axios.post(
      config.base_url_drafts,
      {
        authorId: user.id,
        authorName: user.username,
        content: text,
        id,
        originalArticleId,
      },
      {
        headers: authHeader(),
      }
    );
  }

  getArticleByUser() {
    const userId = userService.getCurrentUser().id;
    return axios.get(config.base_url_article_user + userId, {
      headers: authHeader(),
    });
  }

  getArticleById(id) {
    return axios.get(API_URL + id, { headers: authHeader() });
  }

  deleteArticleById(id) {
    return axios.delete(API_URL + id, { headers: authHeader() });
  }

  deleteDraftById(id) {
    return axios.delete(config.base_url_drafts + id, { headers: authHeader() });
  }

  getAllDrafts() {
    return axios.get(config.base_url_drafts, {
      headers: authHeader(),
    });
  }

  approveDraft(id) {
    return axios.put(config.base_url_drafts_approve + id, null, {
      headers: authHeader(),
    });
  }

  disApproveDraft(id) {
    return axios.put(config.base_url_drafts_disapprove + id, null, {
      headers: authHeader(),
    });
  }

  getMyDrafts() {
    return axios.get(config.base_url_drafts_self, { headers: authHeader() });
  }
}

export default new ArticleService();
