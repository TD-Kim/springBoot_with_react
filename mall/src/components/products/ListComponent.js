import React, { useEffect, useState } from "react";
import useCustomMove from "../../hooks/useCustomMove";
import { API_SERVER_HOST, getList } from "../../api/productsApi";
import FetchingModal from "../common/FetchingModal";
import PageComponent from "../common/PageComponent";
import { useQuery, useQueryClient } from "@tanstack/react-query";

const initState = {
  dtoList: [],
  pageNumList: [],
  pageRequestDTO: null,
  prev: false,
  next: false,
  totalCount: 0,
  prevPage: 0,
  nextPage: 0,
  totalPage: 0,
  current: 0,
};

const host = API_SERVER_HOST;

function ListComponent(props) {
  // const [serverData, setServerData] = useState(initState);
  // const [fetching, setFetching] = useState(false);

  const { moveToList, moveToRead, page, size, refresh } = useCustomMove();

  // useEffect(() => {
  //   setFetching(true);

  //   getList({ page, size }).then((data) => {
  //     setServerData(data);
  //     setFetching(false);
  //   });
  // }, [page, size, refresh]);

  const { data, isFetching, error, isError } = useQuery({
    // queryKey: ["products/list", { page, size }],
    queryKey: ["products/list", { page, size, refresh }],
    queryFn: () => getList({ page, size }),
    staleTime: 1000 * 10,
  });

  // const queryClient = useQueryClient();

  const handleClickPage = (pageParam) => {
    // if (pageParam.page === parseInt(page))
    //   queryClient.invalidateQueries("products/list"); // 무효화 시켜라
    moveToList(pageParam);
  };

  const serverData = data || initState;

  return (
    <div className="border-2 border-blue-100 mt-10 mr-2 ml-2">
      {/* {fetching ? <FetchingModal /> : <></>} */}
      {isFetching ? <FetchingModal /> : <></>}

      <div className="flex flex-wrap mx-auto p-6">
        {serverData.dtoList.map((product) => (
          <div
            key={product.pno}
            className="w-1/2 p-1 rounded shadow-md border-2"
            onClick={() => moveToRead(product.pno)}
          >
            <div className="flex flex-col  h-full">
              <div className="font-extrabold text-2xl p-2 w-full ">
                {product.pno}
              </div>
              <div className="text-1xl m-1 p-2 w-full flex flex-col">
                <div className="w-full overflow-hidden ">
                  <img
                    alt="product"
                    className="m-auto rounded-md w-60"
                    src={`${host}/api/products/view/s_${product.uploadFileNames[0]}`}
                  />
                </div>

                <div className="bottom-0 font-extrabold bg-white">
                  <div className="text-center p-1">이름: {product.pname}</div>
                  <div className="text-center p-1">가격: {product.price}</div>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>

      <PageComponent
        serverData={serverData}
        // movePage={moveToList}
        movePage={handleClickPage}
      ></PageComponent>
    </div>
  );
}

export default ListComponent;
