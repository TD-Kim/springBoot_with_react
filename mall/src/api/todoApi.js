// import axios from "axios";
import jwtAxios from "../util/jwtUtil";

export const API_SERVER_HOST = "http://localhost:8080";

const prefix = `${API_SERVER_HOST}/api/todo`;

export const getOne = async (tno) => {
  // const res = await axios.get(`${prefix}/${tno}`);
  const res = await jwtAxios.get(`${prefix}/${tno}`);

  return res.data;
};

export const getList = async (pageParam) => {
  const { page, size } = pageParam;

  // const res = await axios.get(`${prefix}/list`, { params: { ...pageParam } });
  const res = await jwtAxios.get(`${prefix}/list`, {
    params: { ...pageParam },
  });

  return res.data;
};

export const postAdd = async (todoObj) => {
  //JSON.stringify(obj) => JSON문자열
  // const res = await axios.post(`${prefix}/`, todoObj); // axios 는 JSON 문자열로 안바꿔도 된다.
  const res = await jwtAxios.post(`${prefix}/`, todoObj);

  return res.data;
};

export const deleteOne = async (tno) => {
  // const res = await axios.delete(`${prefix}/${tno}`);
  const res = await jwtAxios.delete(`${prefix}/${tno}`);

  return res.data;
};

export const putOne = async (todo) => {
  // const res = await axios.put(`${prefix}/${todo.tno}`, todo);
  const res = await jwtAxios.put(`${prefix}/${todo.tno}`, todo);

  return res.data;
};
