const requestPay = (id) => {
  let cash = $('input[name=point-quantity]:checked').val();

  const IMP = window.IMP;
  IMP.init('imp87611393');

  IMP.request_pay({
    pg: "kakaopay",
    pay_method: 'card',
    name: '결제',
    amount: cash,
  }, function (rsp) {
    //결제 성공시 구매한 포인트를 해당 회원 컬럼에 추가
    if (rsp.success) {
      $.ajax({
        type: "get",
        url: "/member/kkoPay",
        data: {"id": id, "cash": cash},
        dataType: "text",
        success: function (result) {
          if (result === "ok") {
            Swal.fire({
              text: '포인트 충전이 완료되었어요!',
              icon: 'success',
              showConfirmButton: false,
              timer: 1500
            });
          } else {
            Swal.fire({
              text: '오류가 발생했어요! 관리자에게 문의하세요.',
              icon: 'error',
              showConfirmButton: false,
              timer: 1500
            });
          }
        }
      });

      //포인트 충전내역에 해당 기록 save
      $.ajax({
        type: "get",
        url: "/point/point-history-save",
        data: {"id": id, "cash": cash},
        dataType: "text",
        success: function (result) {

        }
      });

      location.reload();
    }
  });
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
};

window.onload = () => {
  const cash = parseInt($('input[name=point-quantity]:checked').val());
  const pointHave = parseInt(document.getElementById('point-have').innerHTML);
  const pointAfterHave = document.getElementById('point-after-have');
  const price = document.getElementById('price');

  pointAfterHave.innerHTML = (cash + pointHave) + ' 포인트';
  price.innerHTML = cash + ' 원';
};
