import { configureStore } from "@reduxjs/toolkit";
import loginSlice from "./slices/loginSlice";

export default configureStore({
  // store 는 금고라고 생각
  reducer: {
    // reducer 는 금고를 지키는 녀석이라고 생각
    loginSlice: loginSlice,
  },
});
