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

  switch (genre) {
    case 1:
      document.getElementById('g1').setAttribute('selected', 'true');
      break;
    case 2:
      document.getElementById('g2').setAttribute('selected', 'true');
      break;
    case 3:
      document.getElementById('g3').setAttribute('selected', 'true');
      break;
    case 4:
      document.getElementById('g4').setAttribute('selected', 'true');
      break;
    case 5:
      document.getElementById('g5').setAttribute('selected', 'true');
      break;
  }

  switch (status) {
    case '연재':
      document.getElementById('s1').setAttribute('selected', 'true');
      break;
    case '완결':
      document.getElementById('s2').setAttribute('selected', 'true');
      break;
    case '휴재':
      document.getElementById('s3').setAttribute('selected', 'true');
      break;
  }
}