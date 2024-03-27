import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { loginPost } from "../api/memberApi";
import { getCookie, removeCookie, setCookie } from "../util/cookieUtil";

// 유지해야 하는 값
const initState = {
  email: "",
};

const loadMemberCookie = () => {
  const memberInfo = getCookie("member");
  return memberInfo;
};

export const loginPostAsync = createAsyncThunk("loginPostAsync", (param) =>
  loginPost(param)
);

const loginSlice = createSlice({
  name: "loginSlice",
  initialState: loadMemberCookie() || initState,
  reducers: {
    login: (state, action) => {
      // 리듀서 함수의 파라미터는 두개밖에 못받음.
      console.log("loginParam", action);
      console.log(action.payload);
      console.log("----------------");

      setCookie("member", JSON.stringify(action.payload), 1);

      // return { email: action.payload.email }; // 리턴값은 다음의 값을 이렇게 유지해줘. 원하는 다음 next 결과값. useSelector 에서 받는다.
      return action.payload; // 리턴값은 다음의 값을 이렇게 유지해줘. 원하는 다음 next 결과값. useSelector 에서 받는다.
    },
    logout: () => {
      console.log("logout...........");
      removeCookie("member");
      return { ...initState };
    },
  },
  // extraReducers 프로퍼티를 사용하는 경우는 이미 다른 곳에서 정의된 액션생성함수를 사용할때인데,
  // 가장 흔한 케이스는 비동기를 위해 createAsyncThunk 를 사용하여 정의된 액션함수를 사용하거나,
  // 다른 slice 에서 정의된 액션함수를 사용하는 경우이다.
  extraReducers: (builder) => {
    builder
      .addCase(loginPostAsync.fulfilled, (state, action) => {
        console.log("fulfilled");
        const payload = action.payload;
        if (!payload.error) {
          setCookie("member", JSON.stringify(payload));
        }
        return payload;
      })
      .addCase(loginPostAsync.pending, (state, action) => {
        console.log("pending");
      })
      .addCase(loginPostAsync.rejected, (state, action) => {
        console.log("rejected");
      });
  },
});

export const { login, logout } = loginSlice.actions;

export default loginSlice.reducer;
