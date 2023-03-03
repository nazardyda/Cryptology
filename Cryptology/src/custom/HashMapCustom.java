package custom;



public class HashMapCustom<K, V>{
    private Entry<K, V>[] table;
    private int capacity = 16;



    static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public HashMapCustom() {
        table = new Entry[capacity];
    }

    public void put(K newKey, V data) {
        if (newKey == null) {
            return; // не підтримуємо ключі зі значенням null
        }

        // Обчислюємо хеш-код ключа та індекс в таблиці
        int hash = newKey.hashCode();
        int index = hash % capacity;

        Entry<K, V> newEntry = new Entry<K, V>(newKey, data, null);

        // Якщо немає жодного елемента на даному індексі, то просто додаємо елемент
        if (table[index] == null) {
            table[index] = newEntry;
        } else {
            Entry<K, V> previous = null;
            Entry<K, V> current = table[index];

            // Проходимося по всіх елементах на даному індексі та перевіряємо, чи вже є такий ключ
            while (current != null) {
                if (current.key.equals(newKey)) {
                    newEntry.next = current.next;
                    if(previous == null) {
                        // Якщо знайдений елемент - перший на даному індексі, то замінюємо його новим елементом
                        table[index] = newEntry;
                    } else {
                        // Якщо знайдений елемент - не перший на даному індексі, то замінюємо його новим елементом
                        previous.next = newEntry;
                    }
                    return;
                }
                previous = current;
                current = current.next;
            }

            // Якщо не знайдено елемент з таким ключем на даному індексі, то додаємо його в кінець списку
            previous.next = newEntry;
        }
    }
    public V get(K key) {
        if (key == null) {
            return null;
        }

        // Обчислюємо хеш-код ключа та індекс в таблиці
        int hash = key.hashCode();
        int index = hash % capacity;
        //визначається індекс в таблиці (масиві), за яким потрібно шукати
        Entry<K, V> current = table[index];
        //шукаємо запис з ключем
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }
}
