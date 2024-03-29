import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { getCartItems, postChangeCart } from "../api/cartApi";
import useCustomLogin from "../hooks/useCustomLogin";

// extra reducer를 동작시키기 위해 createAsyncThunk 로 감싼것.
export const getCartItemsAsync = createAsyncThunk("getCartItemsAsync", () => {
  // const { loginState } = useCustomLogin();
  // if (!loginState.email) {
  //   return [];
  // }
  return getCartItems();
});

export const postChangeCartAsync = createAsyncThunk(
  "postChangeCartAsync",
  (param) => {
    return postChangeCart(param);
  }
);

const initState = [];

const cartSlice = createSlice({
  name: "cartSlice",
  initialState: initState,
  extraReducers: (builder) => {
    builder
      .addCase(getCartItemsAsync.fulfilled, (state, action) => {
        console.log("getCartItemsAsync.fulfilled");
        console.log(action.payload);

        return action.payload;
      })
      .addCase(postChangeCartAsync.fulfilled, (state, action) => {
        console.log("postChangeCartAsync.fulfilled");
        console.log(action.payload);

        return action.payload;
      });
  },
});

export default cartSlice.reducer;
