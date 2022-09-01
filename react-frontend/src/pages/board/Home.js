import React, { useEffect, useState } from 'react';
import BoardItem from '../../components/BoardItem';
import Paging from '../../components/Paging';
import {useLocation} from "react-router-dom";
const Home = () => {
  const [posts, setPosts] = useState([]);
  const [no, setNo] = useState(0);
  const [count, setCount] = useState('');
  const location = useLocation();
  const keyword = location.state?.keyword ??'';
  const layer = 0;



  useEffect(() => {
    fetch('http://localhost:8080/post?searchKeyword=' + keyword + '&layer=' + layer + '&page=' + no)
      .then((res) => res.json())
      .then((res) => {
        setPosts(res.content);
        setCount(res.totalElements);
      }); //비동기 함수
  }, [keyword, no]);

    useEffect(() => {
        setNo(0);
    },[keyword])


  return (
    <div>
      {posts&&posts.map((post) => (
        <BoardItem key={post.id} post={post} />
      ))}
        <Paging setNo={setNo} count={count} />
    </div>
  );
};

export default Home;
