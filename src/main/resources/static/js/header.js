window.onload = function (){
  console.log("실행");
  let header = $("meta[name='_csrf_header']").attr('content');
  let token = $("meta[name='_csrf']").attr('content');

  $.ajax({
    type: "get",
    url: "/notice/readCount",
    dataType: "boolean",
    beforeSend: function(xhr){
      xhr.setRequestHeader(header, token);
    },
    success: function (result) {
      if (result == true) {
        const icon = document.querySelector('.notification');
        icon.className += 'new-notice';
      } else {
        const icon = document.querySelector('.notification');
        icon.className += 'notification';
      }
    }
  });
}

const checkClick = () => {
  const checkbox = document.querySelector('.isChecked');

  if (!checkbox.checked) {
    checkbox.setAttribute('checked', 'true');
  } else if (checkbox.checked) {
    checkbox.removeAttribute('checked');
  }
}

const requestSignUp = (social) => {
  const object = document.querySelector('.isChecked');
  const beforeHTML = document.querySelector('.hide-body');
  const beforeFooter = document.querySelector('.hide-footer');
  const afterHTML = document.querySelector('.register-form');

  if (!object.checked) {
    Swal.fire({
      icon: 'error',
      text: '개인정보보호정책 및 이용약관에 동의해주세요.',
      showConfirmButton: false,
      timer: 2000
    });
  } else {
    if (social == 'naver') {
      location.href = "/oauth2/authorization/naver";
    } else if (social == 'google') {
      location.href = "/oauth2/authorization/google";
    } else if (social == 'facebook') {
      location.href = "/oauth2/authorization/facebook";
    } else if (social == 'kakao') {
      location.href = "/oauth2/authorization/kakao";
    } else if (social == 'github') {
      location.href = "/oauth2/authorization/github";
    } else {
      beforeHTML.style.display = 'none';
      beforeHTML.setAttribute('aria-hidden', 'true');
      beforeFooter.style.display = 'none';
      beforeFooter.setAttribute('aria-hidden', 'true');

      afterHTML.style.display = 'block';
      afterHTML.setAttribute('aria-hidden', 'false');
    }
  }
}