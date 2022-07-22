const requestPay = () => {
  const cash = $('input[name=point-quantity]:checked').val();
  alert(cash);
}

const changePoint = () => {
  let cash = parseInt($('input[name=point-quantity]:checked').val());
  const pointHave = parseInt(document.getElementById('point-have').innerHTML);
  const pointAfterHave = document.getElementById('point-after-have');
  const price = document.getElementById('price');
  if (cash >= 10000) {
    cash = cash / 10 + cash;
  }

  pointAfterHave.innerHTML = (cash + pointHave) + ' 포인트';
  price.innerHTML = cash + ' 원';
}

window.onload = () => {
  const cash = parseInt($('input[name=point-quantity]:checked').val());
  const pointHave = parseInt(document.getElementById('point-have').innerHTML);
  const pointAfterHave = document.getElementById('point-after-have');
  const price = document.getElementById('price');

  pointAfterHave.innerHTML = (cash + pointHave) + ' 포인트';
  price.innerHTML = cash + ' 원';
};