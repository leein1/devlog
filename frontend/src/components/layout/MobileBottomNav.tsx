const navItems = [
  { icon: 'home', filled: true, active: true },
  { icon: 'search', filled: false, active: false },
  { icon: 'collections_bookmark', filled: false, active: false },
  { icon: 'person', filled: false, active: false },
];

export default function MobileBottomNav() {
  return (
    <div className="md:hidden fixed bottom-6 left-1/2 -translate-x-1/2 w-[90%] glass-effect bg-white/80 rounded-full px-8 py-4 flex justify-around items-center z-50 shadow-2xl">
      {navItems.map((item) => (
        <span
          key={item.icon}
          className={`material-symbols-outlined ${item.active ? 'text-[#003EC7]' : 'text-[#434656]'}`}
          style={item.filled ? { fontVariationSettings: "'FILL' 1" } : undefined}
        >
          {item.icon}
        </span>
      ))}
    </div>
  );
}
