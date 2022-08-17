const requestAuthor = () => {
  if (role != 'ROLE_WRITER'){
    Swal.fire({
      text: '작가 신청을 할까요?',
      showDenyButton: true,
      confirmButtonText: '네!',
      denyButtonText: '아니요..'
    }).then((result) => {
      let header = $("meta[name='_csrf_header']").attr('content');
      let token = $("meta[name='_csrf']").attr('content');
      if (result.isConfirmed) {
        $.ajax({
          type: 'post',
          url: '/member/req-writer-save',
          data: {"id": id},
          dataType: 'text',
          beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
          },
          success: function (result) {
            if (result === 'ok') {
              Swal.fire('작가 신청을 완료했어요!', '', 'success');
            } else {
              Swal.fire('오류가 발생했어요, 관리자에게 문의하세요.', '', 'error');
            }
          }
        });
      } else if (result.isDenied) {
        Swal.fire('작가 신청을 취소했어요', '', 'info')
      }
    });
  } else {
    Swal.fire('이미 작가로 등록되어 있습니다.');
  }
}

const goBack = () => {
  location.href = "/member/myPage/" + id;
}