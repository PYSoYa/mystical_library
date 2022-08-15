window.onload = () => {
  const topButton = document.getElementById('moveTop');
  const spreadButton = document.querySelector('.spread');
  const spreadSpan = document.querySelector('.spread-span');
  const listNav = document.querySelector('.quick-list-nav');
  const optionButton = document.querySelector('.view-option-wrap');
  const optionPanel = document.querySelector('.option-panel');
  const darkButton = document.querySelector('.dark-mode-btn-wrap');

  document.addEventListener('scroll', function () {
    if (document.documentElement.scrollTop === 0) {
      topButton.className = '';
      darkButton.classList.remove('on');
    } else {
      topButton.className = 'on';
      darkButton.classList.add('on');
    }
  });

  topButton.addEventListener('click', function () {
    document.documentElement.scrollTop = 0;
  });

  spreadButton.addEventListener('click', function () {
    if (spreadSpan.className === 'spread-span' && listNav.className === 'quick-list-nav') {
      spreadSpan.className = 'active';
      listNav.className += ' active-list';
    } else {
      spreadSpan.className = 'spread-span';
      listNav.className = 'quick-list-nav';
    }
  });

  optionButton.addEventListener('click', function () {
    if (optionButton.className === 'view-option-wrap' && optionPanel.className === 'option-panel') {
      optionButton.className += ' active';
      optionPanel.className += ' active-option-list';
    } else {
      optionButton.className = 'view-option-wrap';
      optionPanel.className = 'option-panel';
    }
  });

  let wrap = document.querySelector(".viewer");
  let bar = document.querySelector('.ui-slider-range');
  let per = document.querySelector('.ui-slider-handle');

  wrap.addEventListener('scroll', function(){
    let scrollTop = wrap.scrollTop;
    let scrollHeight = wrap.scrollHeight - wrap.clientHeight;
    let percentage = (scrollTop/scrollHeight) * 100;
    if(isNaN(percentage)) percentage = 0;
    bar.style.width = percentage + '%';
    per.style.left = percentage + '%';
    per.innerText = Math.floor(percentage) + '%';
  });

  document.getElementById('toggle').addEventListener("click", e => {
    if (document.body.className === '') {
      document.body.classList.add("dark-mode");

      document.querySelector('.viewer').classList.add('dark');
      document.querySelector('.content').classList.add('dark');
      document.querySelector('.content-footer').classList.add('dark');
      if (document.querySelector('.star-desc') != null) {
        document.querySelector('.star-desc').classList.add('dark');
      }
      if (document.querySelector('.rate-desc') != null) {
        document.querySelector('.rate-desc').classList.add('dark');
      }
      if (document.querySelector('.edit-btn') != null) {
        document.querySelector('.edit-btn').classList.add('dark');
      }
      if (document.querySelector('.delete-btn') != null) {
        document.querySelector('.delete-btn').classList.add('dark');
      }
    } else {
      document.body.classList.remove("dark-mode");

      document.querySelector('.viewer').classList.remove('dark');
      document.querySelector('.content').classList.remove('dark');
      document.querySelector('.content-footer').classList.remove('dark');
      document.querySelector('.star-desc').classList.remove('dark');
      if (document.querySelector('.star-btn') != null) {
        document.querySelector('.star-btn').classList.remove('dark');
      }
      if (document.querySelector('.rate-desc') != null) {
        document.querySelector('.rate-desc').classList.remove('dark');
      }
      if (document.querySelector('.edit-btn') != null) {
        document.querySelector('.edit-btn').classList.remove('dark');
      }
      if (document.querySelector('.delete-btn') != null) {
        document.querySelector('.delete-btn').classList.remove('dark');
      }
    }
  },false);

  if (document.querySelector('body').className === 'dark-mode') {
  }
}