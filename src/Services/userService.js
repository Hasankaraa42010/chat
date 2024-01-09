import axios from 'axios';

export default class UserService {
  // Diğer fonksiyonlar burada olabilir

  loginUser(email, password) {
    const url = `http://localhost:8093/login?email=${email}&password=${password}`;

    return axios.get(url)
      .then(response => {
        console.log(response.data); 
        return response.data;
      })
      .catch(error => {
        console.error('Hata:', error); 
        throw error;
      });
  }
  userFriends(userId) {
    const url = `http://localhost:8093/user/allFriend?userId=${userId}`;

    return axios.get(url)
      .then(response => {
        console.log(response.data); 
        return response.data;
      })
      .catch(error => {
        console.error('Hata:', error); 
        throw error;
      });
  }
}
