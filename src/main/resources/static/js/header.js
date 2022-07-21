const checkClick = () => {
  const checkbox = document.querySelector('.isChecked');

  if (!checkbox.checked) {
    checkbox.setAttribute('checked', 'true');
  } else if (checkbox.checked) {
    checkbox.removeAttribute('checked');
  }
}

const requestSignUp = () => {
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
    beforeHTML.style.display = 'none';
    beforeHTML.setAttribute('aria-hidden', 'true');
    beforeFooter.style.display = 'none';
    beforeFooter.setAttribute('aria-hidden', 'true');

    afterHTML.style.display = 'block';
    afterHTML.setAttribute('aria-hidden', 'false');
  }
}