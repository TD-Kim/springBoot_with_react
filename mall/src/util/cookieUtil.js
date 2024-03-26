import { Cookies } from "react-cookie";

const cookies = new Cookies();

export const setCookie = (name, value, days = 1) => {
  const expires = new Date();
  expires.setUTCDate(expires.getUTCDate() + days); // 보관기한

  // path 는 어느 경로에서 쿠키를 사용할지이기 때문에 '/'로 해야 하위 경로에서도 다 사용할 수 있다.
  return cookies.set(name, value, { expires: expires, path: "/" });
};

export const getCookie = (name) => {
  return cookies.get(name);
};

export const removeCookie = (name, path = "/") => {
  cookies.remove(name, { path: path });
};
