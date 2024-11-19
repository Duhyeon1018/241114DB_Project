package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class InsertTable {
	public static ArrayList<String> InsertData = new ArrayList<>();

// 작가 이름 리스트
	public static ArrayList<String> authors = new ArrayList<>(
			Arrays.asList("J.K. Rowling", "George R.R. Martin", "Stephen King", "Agatha Christie", "J.R.R. Tolkien",
					"Haruki Murakami", "Jane Austen", "Mark Twain", "Charles Dickens", "Leo Tolstoy",
					"Fyodor Dostoevsky", "Ernest Hemingway", "Gabriel Garcia Marquez", "Franz Kafka", "Victor Hugo",
					"Arthur Conan Doyle", "Isaac Asimov", "Edgar Allan Poe", "Oscar Wilde", "H.P. Lovecraft"));

// 출판사 이름 리스트
	public static ArrayList<String> publishers = new ArrayList<>(Arrays.asList("Penguin Random House", "HarperCollins",
			"Simon & Schuster", "Hachette Book Group", "Macmillan Publishers", "Scholastic", "Oxford University Press",
			"Pearson", "Bloomsbury Publishing", "Cengage", "Wiley", "Springer", "Elsevier", "Vintage Books",
			"Pan Macmillan", "DK Publishing", "Tor Books", "Bantam Books", "Little, Brown and Company", "Ecco Press"));

// 장르 이름 리스트
	public static ArrayList<String> genres = new ArrayList<>(
			Arrays.asList("Fantasy", "Science Fiction", "Mystery", "Thriller", "Romance", "Historical Fiction",
					"Horror", "Biography", "Self-Help", "Poetry", "Non-Fiction", "Adventure", "Graphic Novel",
					"Children Literature", "Young Adult", "Classic", "Crime", "Drama", "Philosophy", "Short Stories"));

	public static ArrayList<String> bookTitles = new ArrayList<>(Arrays.asList("The Shadow of Eternity",
			"Whispers of the Wind", "Beneath the Crimson Sky", "Echoes of the Forgotten", "The Last Enigma",
			"Shattered Reflections", "A Symphony of Stars", "Wanderlust Chronicles", "The Keeper of Secrets",
			"The Alchemist Code", "Threads of Destiny", "A Kingdom of Ashes", "The Edge of Tomorrow",
			"Dreams of the Unknown", "The Sands of Time", "Mysteries of the Deep", "The Forgotten Voyage",
			"Fate Redemption", "Legacy of the Fallen", "Labyrinth of Lies", "A Whisper of Shadows",
			"The Midnight Tides", "Echoes in the Abyss", "The Eternal Flame", "The Hidden Chronicle",
			"Secrets of the Oracle", "Winds of Change", "The Iron Citadel", "The Pale Horizon", "A Tale of Two Worlds",
			"The Clockwork Kingdom", "The Starbound Heir", "A Mirror of Ice", "The Vanished Realm", "The Phoenix Rise",
			"Tales of the Moonlit Path", "The Crimson Throne", "The Silent Kingdom", "The Wanderer Compass",
			"A Dance of Blades", "The Emerald Prophecy", "Voices of the Ancients", "A Path of Thorns",
			"The Shimmering Veil", "The Spiral Staircase", "The Lost Symphony", "The Golden Labyrinth",
			"The Winter Heir", "Shadows of the Past", "The Rising Storm", "A Legacy in Flames",
			"The Forgotten Prophecy", "The Stolen Crown", "The Lantern Glow", "The Silver Masquerade",
			"A Whisper in the Dark", "The Final Odyssey", "The Mystic Journey", "The Cursed Kingdom",
			"The Merchant Tale", "The Enchanted Hourglass", "A Rift in Time", "The Binding Curse",
			"Chronicles of the Silent Woods", "The Seeker Light", "A Symphony of Shadows", "The Starborn Chronicles",
			"The Warden Oath", "The Spiral of Fate", "The Glass Horizon", "The Veil of Dreams", "The Crimson Seal",
			"A Cloak of Feathers", "The Eternal Voyage", "The Sapphire Song", "A Kingdom for the Lost",
			"The Nightwatcher Gambit", "The Ashen Crown", "The Whispering Forest", "The Warlock Gambit",
			"The Moonlit Haven", "The Horizon of Echoes", "The Storm Embrace", "The Ivory Key", "The Harbinger Call",
			"The Forgotten Tides", "The Sky Edge", "The Gilded Cage", "The Obsidian Mirror", "The Silent Passage",
			"A World Apart", "The Path of Echoes", "The Last Sanctuary", "The Specter Grasp", "The Eternal Spire",
			"The Shattered Kingdom", "The Shadow Edge", "The Lunar Tapestry", "The Fractured Realm"));

	static {
		for (String authorName : authors) {
			InsertData.add("INSERT INTO KAUTHORTABLE (AUTHORNAME) VALUES ('" + authorName + "')");
		}
		for (String publisherName : publishers) {
			InsertData.add("INSERT INTO KPUBLISHERTABLE (PUBLISHERNAME) VALUES ('" + publisherName + "')");
		}
		for (String genreName : genres) {
			InsertData.add("INSERT INTO KGENRETABLE (GENRETABLENAME) VALUES ('" + genreName + "')");
		}
		Random rand = new Random();
		int inputIndex = 1;
		int appendInt = 1;
		for (int i = 1; i <= bookTitles.size(); i++) {
			int randomCountInt = rand.nextInt(56);
			InsertData.add("INSERT INTO KBOOKTABLE (BOOKNAME, AUTHORNO, PUBLISHERNO, COUNT) VALUES ('"
					+ bookTitles.get(i - 1) + "', " + inputIndex + ", " + inputIndex + ", " + randomCountInt + ")");
			InsertData.add("INSERT INTO KGENREBOOKTABLE (GENRETABLENO, BOOKNO) VALUES (" + inputIndex + ", " + i + ")");
			inputIndex += appendInt;
			if (inputIndex == 20) {
				appendInt = -1;
			}
			if (inputIndex == 1) {
				appendInt = 1;
			}
		}
	}

	public static void inputData() {
		for (String sql : InsertData) {
			CgwDAO.executeUpdateSql(sql);
		}
	}

	public static void main(String[] args) {
		inputData();
	}
}