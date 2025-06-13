// Hero Slider Functionality
let currentSlide = 0;
const slides = document.querySelectorAll('.slide');
const totalSlides = slides.length;

function showSlide(index) {
	slides.forEach(slide => slide.classList.remove('active'));
	slides[index].classList.add('active');
}

function nextSlide() {
	currentSlide = (currentSlide + 1) % totalSlides;
	showSlide(currentSlide);
}

// Auto-advance slides every 5 seconds
setInterval(nextSlide, 5000);

// Mobile menu toggle (basic functionality)
const hamburger = document.querySelector('.hamburger');
const navLinks = document.querySelector('.nav-links');

hamburger.addEventListener('click', () => {
	navLinks.style.display = navLinks.style.display === 'flex' ? 'none' : 'flex';
});

// Smooth scrolling for anchor links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
	anchor.addEventListener('click', function(e) {
		e.preventDefault();
		const target = document.querySelector(this.getAttribute('href'));
		if (target) {
			target.scrollIntoView({
				behavior: 'smooth',
				block: 'start'
			});
		}
	});
});