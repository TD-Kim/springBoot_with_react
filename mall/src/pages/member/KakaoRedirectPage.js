import React, { useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { getAccessToken, getMemberWithAccessToken } from "../../api/kakaoApi";
import { login } from "../../slices/loginSlice";
import { useDispatch } from "react-redux";
import useCustomLogin from "../../hooks/useCustomLogin";

function KakaoRedirectPage(props) {
  const [searchParams] = useSearchParams();

  const { moveToPath, saveAsCookie } = useCustomLogin();

  const authCode = searchParams.get("code");

  // const dispatch = useDispatch();

  useEffect(() => {
    getAccessToken(authCode).then((accessToken) => {
      // console.log(data); // kakao accessToken 은 시간이 굉장히 짧다. 1분인가 30초인가..

      getMemberWithAccessToken(accessToken).then((memberInfo) => {
        console.log("-----------------------------------");
        console.log(memberInfo);

        // dispatch(login(memberInfo));
        saveAsCookie(memberInfo);

        if (memberInfo && memberInfo.social) {
          moveToPath("/member/modify"); // 회원정보 수정하러 가야함
        } else {
          moveToPath("/");
        }
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
