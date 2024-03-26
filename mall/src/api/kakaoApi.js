import axios from "axios";
import { API_SERVER_HOST } from "./memberApi";

const rest_api_key = "acd35c23a20869435e7b0f2744c0b630";
const redirect_uri = "http://localhost:3000/member/kakao";

const auth_code_path = "https://kauth.kakao.com/oauth/authorize";

const access_token_uri = "https://kauth.kakao.com/oauth/token";

export const getKakaoLoginLink = () => {
  const kakaoURL = `${auth_code_path}?client_id=${rest_api_key}&redirect_uri=${redirect_uri}&response_type=code`;

  return kakaoURL;
};

export const getAccessToken = async (authCode) => {
  const header = {
    headers: {
      "Content-Type": "application/x-www-form-urlencoded;charset=utf-8",
    },
  };

  const params = {
    grant_type: "authorization_code",
    client_id: rest_api_key,
    redirect_uri: redirect_uri,
    code: authCode,
  };

  const res = await axios.post(access_token_uri, params, header);

  const { access_token } = res.data;

  return access_token;
};

export const getMemberWithAccessToken = async (accessToken) => {
  const res = await axios.get(
    `${API_SERVER_HOST}/api/member/kakao?accessToken=${accessToken}`
  );

  return res.data;
};
