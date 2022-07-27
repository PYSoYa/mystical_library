const requestAuthor = () => {
  const id = [[${member.id}]]
  Swal.fire({
    title: '작가 신청을 할까요?',
    showDenyButton: true,
    confirmButtonText: '네!',
    denyButtonText: '아니요..'
  }).then((result) => {
    if (result.isConfirmed) {
      $.ajax({
        type: 'post',
        url: '/admin/req-writer-save',
        data: {"id": id},
        dataType: 'text',
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
}