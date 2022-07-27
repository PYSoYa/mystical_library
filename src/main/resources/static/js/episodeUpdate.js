window.onload = () => {
  switch (payment) {
    case 0:
      document.getElementById('p0').setAttribute('selected', 'true');
      break;
    case 1:
      document.getElementById('p1').setAttribute('selected', 'true');
      break;
  }
}