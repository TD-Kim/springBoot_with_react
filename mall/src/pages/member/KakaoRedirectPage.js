import React, { useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { getAccessToken, getMemberWithAccessToken } from "../../api/kakaoApi";

function KakaoRedirectPage(props) {
  const [searchParams] = useSearchParams();

  const authCode = searchParams.get("code");

  useEffect(() => {
    getAccessToken(authCode).then((accessToken) => {
      // console.log(data); // kakao accessToken 은 시간이 굉장히 짧다. 1분인가 30초인가..

      getMemberWithAccessToken(accessToken).then((result) => {
        console.log("-----------------------------------");
        console.log(result);
      });
    });
  }, [authCode]);

  return (
    <div>
      <div>Kakao Login Redirect</div>
      <div>{authCode}</div>
    </div>
  );
}

export default KakaoRedirectPage;
