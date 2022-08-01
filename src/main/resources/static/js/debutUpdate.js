const resize = (obj) => {
  obj.style.height = '11.3rem';
  obj.style.height = (12 + obj.scrollHeight) + 'px';
}

window.onload = () => {

  switch (category) {
    case 1:
      document.getElementById('c1').setAttribute('selected', 'true');
      break;
    case 2:
      document.getElementById('c2').setAttribute('selected', 'true');
      break;
    case 3:
      document.getElementById('c3').setAttribute('selected', 'true');
      break;
  }
}