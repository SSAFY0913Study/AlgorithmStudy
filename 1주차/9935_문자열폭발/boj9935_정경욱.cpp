<<<<<<< HEAD
#include <iostream>
#include <vector>
#include <algorithm>
#include <string.h>
#include <string>
#include <stack>
#include <deque>
using namespace std;

string example, bomb, result = "";

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	cin >> example >> bomb;

	char bombLastChar = bomb[bomb.size() - 1];	// ���߹��ڿ��� ������ ����

	int strSize = example.size();	// �Է¹��ڿ��� ����
	int bombSize = bomb.size();		// ���߹��ڿ��� ����

	for (int i = 0; i < strSize; i++) {
		result.push_back(example[i]);	//��� ���ڿ��� �߰�
		if (example[i] == bombLastChar) {	// ���� �˻��ϴ� ���ڰ� ���߹��ڿ��� ������ ���ڰ� ���ٸ� ������ �� �ִ��� �˻��Ѵ�.
			if (result.size() >= bombSize && result.substr(result.size() - bombSize, bombSize) == bomb) {	// ������ �� �ִ� ���� 1. ������� ���ڿ� ���̰� ���߹��ڿ��� ���̺��� ũ�ų� ���ƾ��Ѵ�. ���� 2. ������� �ڿ������� ���߹��ڿ��� ���̸�ŭ �ڸ� ���� ���߹��ڿ��� ���ٸ� �����Ѵ�.
				result.erase(result.size() - bombSize);	//���ڿ��� �����ϸ� ��������.
			}
		}
	}

	if (result.size() == 0) cout << "FRULA";
	else cout << result;

	return 0;
=======
#include <iostream>
#include <vector>
#include <algorithm>
#include <string.h>
#include <string>
#include <stack>
#include <deque>
using namespace std;

string example, bomb, result = "";

int main() {
	ios_base::sync_with_stdio(false);
	cin.tie(NULL);
	cout.tie(NULL);

	cin >> example >> bomb;

	char bombLastChar = bomb[bomb.size() - 1];	// ���߹��ڿ��� ������ ����

	int strSize = example.size();	// �Է¹��ڿ��� ����
	int bombSize = bomb.size();		// ���߹��ڿ��� ����

	for (int i = 0; i < strSize; i++) {
		result.push_back(example[i]);	//��� ���ڿ��� �߰�
		if (example[i] == bombLastChar) {	// ���� �˻��ϴ� ���ڰ� ���߹��ڿ��� ������ ���ڰ� ���ٸ� ������ �� �ִ��� �˻��Ѵ�.
			if (result.size() >= bombSize && result.substr(result.size() - bombSize, bombSize) == bomb) {	// ������ �� �ִ� ���� 1. ������� ���ڿ� ���̰� ���߹��ڿ��� ���̺��� ũ�ų� ���ƾ��Ѵ�. ���� 2. ������� �ڿ������� ���߹��ڿ��� ���̸�ŭ �ڸ� ���� ���߹��ڿ��� ���ٸ� �����Ѵ�.
				result.erase(result.size() - bombSize);	//���ڿ��� �����ϸ� ��������.
			}
		}
	}

	if (result.size() == 0) cout << "FRULA";
	else cout << result;

	return 0;
>>>>>>> 3c536fed544afbeccb1b8ff7547975bf1dbaac01
}